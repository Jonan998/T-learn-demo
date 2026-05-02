import http from "k6/http";
import { check, group, sleep } from "k6";

export const options = {
  vus: Number(__ENV.VUS || 200),
  duration: __ENV.DURATION || "60s",
  thresholds: {
    http_req_duration: ["p(95)<800"],
    http_req_failed: ["rate<0.2"],
  },
};

const BASE_URL = __ENV.BASE_URL || "http://217.71.129.139:4186";
const USERNAME = __ENV.K6_USER || "john";
const PASSWORD = __ENV.K6_PASSWORD || "QWERTY";

function authHeaders(token) {
  return {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  };
}

function jsonHeaders() {
  return {
    headers: {
      "Content-Type": "application/json",
    },
  };
}

function isExpected(status) {
  return [200, 201, 204, 400, 401, 403, 404, 409].includes(status);
}

export function setup() {
  const loginRes = http.post(
    `${BASE_URL}/auth/login`,
    JSON.stringify({ username: USERNAME, password: PASSWORD }),
    jsonHeaders(),
  );

  check(loginRes, {
    "setup login status 200": (r) => r.status === 200,
    "setup token exists": (r) => !!r.json("token"),
  });

  return { token: loginRes.json("token") };
}

export default function (data) {
  const token = data.token;
  const year = new Date().getUTCFullYear();
  const month = new Date().getUTCMonth() + 1;

  group("/users (AuthenticationPrincipal)", () => {
    const settingsGetRes = http.get(`${BASE_URL}/users/settings`, authHeaders(token));
    check(settingsGetRes, { "GET /users/settings": (r) => isExpected(r.status) });

    const settingsPatchRes = http.patch(
      `${BASE_URL}/users/settings`,
      JSON.stringify({
        limitNew: 10,
        limitRepeat: 15,
        password: PASSWORD,
        newPassword: PASSWORD,
      }),
      authHeaders(token),
    );
    check(settingsPatchRes, { "PATCH /users/settings": (r) => isExpected(r.status) });
  });

  group("/learning (AuthenticationPrincipal)", () => {
    const newWordsRes = http.get(`${BASE_URL}/learning/words/new`, authHeaders(token));
    check(newWordsRes, { "GET /learning/words/new": (r) => isExpected(r.status) });

    const repeatWordsRes = http.get(`${BASE_URL}/learning/words/repeat`, authHeaders(token));
    check(repeatWordsRes, { "GET /learning/words/repeat": (r) => isExpected(r.status) });

    const dictionaryRes = http.get(`${BASE_URL}/learning/dictionary`, authHeaders(token));
    check(dictionaryRes, { "GET /learning/dictionary": (r) => isExpected(r.status) });

    const progressRes = http.patch(
      `${BASE_URL}/learning/progress`,
      JSON.stringify([
        { wordId: 1, studyLevel: 1 },
        { wordId: 2, studyLevel: 2 },
      ]),
      authHeaders(token),
    );
    check(progressRes, { "PATCH /learning/progress": (r) => isExpected(r.status) });

    const statsYearRes = http.get(
      `${BASE_URL}/learning/statistics/year?year=${year}`,
      authHeaders(token),
    );
    check(statsYearRes, { "GET /learning/statistics/year": (r) => isExpected(r.status) });

    const statsMonthRes = http.get(
      `${BASE_URL}/learning/statistics/month?year=${year}&month=${month}`,
      authHeaders(token),
    );
    check(statsMonthRes, { "GET /learning/statistics/month": (r) => isExpected(r.status) });

    const trainingCheckRes = http.post(
      `${BASE_URL}/learning/training/check`,
      JSON.stringify({ wordId: 1, userAnswer: "test" }),
      authHeaders(token),
    );
    check(trainingCheckRes, { "POST /learning/training/check": (r) => isExpected(r.status) });
  });

  group("/dictionary (AuthenticationPrincipal)", () => {
    const dictListRes = http.get(`${BASE_URL}/dictionary`, authHeaders(token));
    check(dictListRes, { "GET /dictionary": (r) => isExpected(r.status) });

    const dictSearchRes = http.get(`${BASE_URL}/dictionary/search?prefix=te`, authHeaders(token));
    check(dictSearchRes, { "GET /dictionary/search": (r) => isExpected(r.status) });

    const dictCreateRes = http.post(
      `${BASE_URL}/dictionary/create`,
      JSON.stringify({
        name: `k6-dict-${__VU}-${__ITER}`,
        description: "k6 dictionary",
        language: "EN",
        isPublic: false,
      }),
      authHeaders(token),
    );
    check(dictCreateRes, { "POST /dictionary/create": (r) => isExpected(r.status) });

    const addWordRes = http.post(
      `${BASE_URL}/dictionary/add`,
      JSON.stringify({ wordId: 1, dictionaryId: 1 }),
      authHeaders(token),
    );
    check(addWordRes, { "POST /dictionary/add": (r) => isExpected(r.status) });

    const removeWordRes = http.del(`${BASE_URL}/dictionary/1/word/1`, null, authHeaders(token));
    check(removeWordRes, {
      "DELETE /dictionary/{dictionaryId}/word/{wordId}": (r) => isExpected(r.status),
    });

    const deleteDictionaryRes = http.del(`${BASE_URL}/dictionary/1`, null, authHeaders(token));
    check(deleteDictionaryRes, { "DELETE /dictionary/{id}": (r) => isExpected(r.status) });
  });

  sleep(1);
}




