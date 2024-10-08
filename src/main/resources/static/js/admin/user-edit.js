// 사용자 수정
document.getElementById('updateBtn').addEventListener('click', function () {
    const updateName = document.getElementById('userName').value; // 수정할 이름

    if (updateName.trim() === "") {
        alert('이름을 입력하세요.');
        return;
    }

    // callApi 함수를 사용하여 수정 API 요청
    callApi('/api/users/' + userId, 'PUT', {userNm: updateName}) // JSON 형태로 보냄
        .then(() => {
            alert('수정되었습니다.');
            location.href = '/admin/users/' + userId; // 회원 상세 페이지로 이동
        })
        .catch(error => {
            alert('오류 발생: ' + error.message);
        });
});