// /static/js/signup.js
document.addEventListener('DOMContentLoaded', function() {
    const registerBtn = document.getElementById('registerBtn');
    if (registerBtn) {
        registerBtn.addEventListener('click', async function(event) {
            event.preventDefault(); // 기본 폼 제출 방지

            const userNm = document.getElementById('userNm').value.trim();
            const userId = document.getElementById('userId').value.trim();
            const userPw = document.getElementById('userPw').value;

            // 입력값 검증
            if (!userId || !userPw || !userNm) {
                alert('이름, 아이디, 비밀번호를 모두 입력해주세요.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8080/api/system-users', { // 실제 REST API 엔드포인트로 변경
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ userId, userPw, userNm })
                });

                if (response.ok) {
                    alert('회원 가입이 성공적으로 완료되었습니다!');
                    window.location.href = '/user/login'; // 가입 성공 시, 로그인 페이지로 리다이렉트
                } else {
                    const errorData = await response.json();
                    alert('회원 가입에 실패했습니다: ' + errorData.message);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('서버와의 통신 중 문제가 발생했습니다.');
            }
        });
    }
});
