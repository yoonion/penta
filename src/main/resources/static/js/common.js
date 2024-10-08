/**
 * 공통 API 요청 함수
 * @param {string} url - 요청할 API URL
 * @param {string} method - HTTP 메서드 (GET, POST, etc.)
 * @param {Object} body - 요청에 포함할 데이터 (JSON 객체)
 * @returns {Promise} - API 응답을 처리하는 Promise
 */
function callApi(url, method, body = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    // body가 있을 경우에만 추가
    if (body) {
        options.body = JSON.stringify(body);
    }

    return fetch(url, options)
        .then(response => {
            if (response.ok) {
                return response.json(); // 성공 시 JSON 응답 처리
            } else {
                return response.json().then(data => {
                    throw new Error(data.message || 'API 요청 실패');
                });
            }
        })
        .catch(error => {
            console.error('API 요청 오류:', error);
            throw error; // 에러를 다시 throw하여 호출부에서 처리 가능
        });
}
