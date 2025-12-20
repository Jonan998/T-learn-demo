import http from "k6/http";
import { check, sleep } from "k6";

export let options = {
  vus: 20,
  duration: "30s",
  thresholds: {
    http_req_duration: ["p(95)<400"],
    http_req_failed: ["rate<0.01"],
  },
};

const BASE = "http://217.71.129.139:5166";

export function setup() {

  const payload = JSON.stringify({
    username: "john",
    password: "qwerty",
  });

  const res = http.post(`${BASE}/auth/login`, payload, {
    headers: { "Content-Type": "application/json" },
  });

  check(res, {
    "login status 200": (r) => r.status === 200,
    "token exists": (r) => !!r.json("token"),
  });

  const token = res.json("token");
  return { token };
}

export default function (data) {
  const headers = {
    Authorization: `Bearer ${data.token}`,
    "Content-Type": "application/json",
  };

  let res1 = http.get(`${BASE}/learning/words/new`, { headers });
  check(res1, { "words/new 200": (r) => r.status === 200 });

  let res2 = http.get(`${BASE}/learning/words/repeat`, { headers });
  check(res2, { "words/repeat 200": (r) => r.status === 200 });

  let res3 = http.get(`${BASE}/users/settings`, { headers });
  check(res3, { "settings GET 200": (r) => r.status === 200 });

  const settingsBody = JSON.stringify({
    limitNew: 10,
    limitRepeat: 10,
  });

  let res4 = http.patch(`${BASE}/users/settings`, settingsBody, { headers });
  check(res4, { "settings PATCH 200": (r) => r.status === 200 });

  const progressBody = JSON.stringify([
    { wordId: 681, studyLevel: 2 },
    { wordId: 588, studyLevel: 1 },
    { wordId: 770, studyLevel: 5 },
    { wordId: 226, studyLevel: 6 },
  ]);

  let res5 = http.patch(`${BASE}/learning/progress`, progressBody, { headers });
  check(res5, { "progress PATCH 200": (r) => r.status === 200 });

   let resDictionary = http.get(`${BASE}/learning/dictionary`, { headers });
    check(resDictionary, { "dictionary 200": (r) => r.status === 200 });


  sleep(1);
}
