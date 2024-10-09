const registerBtn = document.getElementById('registerBtn');

registerBtn.addEventListener('click', function(event) {
    const userNm = document.getElementById('userNm').value.trim();
    const userId = document.getElementById('userId').value.trim();
    const userPw1 = document.getElementById('userPw1').value.trim();
    const userPw2 = document.getElementById('userPw2').value.trim();
    const userAuth = "user";

    // 입력값 검증
    if (!userId || !userPw1 || !userPw2 || !userNm) {
        alert('이름, 아이디, 비밀번호, 비밀번호 확인을 모두 입력해주세요.');
        return;
    }

    // 비밀번호 입력 / 입력 확인 같은지 검증
    if (userPw1 !== userPw2) {
        alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
        return;
    }

    // 공통 함수를 사용하여 회원가입 요청
    callApi('/api/users', 'POST', { userId, userPw: userPw1, userNm, userAuth })
        .then(response => {
            alert('회원 가입이 성공적으로 완료되었습니다!');
            window.location.href = '/user/login'; // 가입 성공 시, 로그인 페이지로 리다이렉트
        })
        .catch(error => {
            displayErrors(error.errors);
            alert('회원 가입에 실패했습니다: ' + error.message);
        });
});

function displayErrors(errors) {
    // 기존 오류 메시지 초기화
    const errorElements = document.querySelectorAll('.error-message');
    errorElements.forEach(element => {
        element.textContent = '';
    });

    // 새로운 오류 메시지 표시
    for (const [field, message] of Object.entries(errors)) {
        const errorElement = document.getElementById(`${field}-error`);
        if (errorElement) {
            errorElement.textContent = message;
        }
    }
}


