/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
		$.ajax({
			type: "DELETE",
			url: "/api/unsubscribe/"+toUserId,
			dataType: "json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.dir(error);
		});
	} else {
		$.ajax({
			type: "POST",
			url: "/api/subscribe/"+toUserId,
			dataType: "json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.dir(error);
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");
	
	$.ajax({
		type: "POST",
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"
	}).done(res=>{
		res.data.forEach((u)=>{
			$("#subscribeModalList").append(getSubscribeModalItem(u));
		})
		
	}).fail(error=>{
		console.dir(error);
	});
}

function getSubscribeModalItem(u) {
	let item = `<div class="subscribe__item" id="subscribeModalItem-1">
		<div class="subscribe__img">
			<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
		</div>
		<div class="subscribe__text">
			<h2>${u.username}</h2>
		</div>
		<div class="subscribe__btn">`;
	
	if(!u.ownerState) { // 동일 유저가 아닐 때만 구독버튼이 보여야함 (ex.다른사용자페이지에서 본인이름 옆에 구독버튼 표시x)
		if(u.subscribeState == 1) { // 구독O 상태->구독취소 버튼 /구독X 상태->구독하기 버튼 노출
			item += `<button class="cta blue" onclick="toggleSubscribeModal(${u.id}, this)">구독취소</button>`;
		} else {
			item += `<button class="cta" onclick="toggleSubscribeModal(${u.id}, this)">구독하기</button>`;
		}
	}
	
	item += `</div>
		</div>`;
	
	return item;
}


// (3) 구독자 정보 모달에서 구독하기, 구독취소
function toggleSubscribeModal(userId, obj) {
	toggleSubscribe(userId, obj);
}

// (4) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {
	// 프로필페이지유저, 로그인유저 동일한지 확인
	if(pageUserId != principalId) {
        alert("프로필을 수정할 수 없는 사용자 입니다.");
        return;
    }

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		
		// 서버에 이미지를 전송
		// form태그 안에 있는 프로필이미지파일을 가지고 와야함
		let profileImageForm = $("userProfileImageForm");
		console.dir(profileImageForm);

        /* jQuery AJAX 요청
        $.ajax({
            url: `/api/profile/upload/${principalId}`, // 서버 엔드포인트
            type: 'POST',
            data: formData,
            processData: false, // 데이터를 문자열로 변환하지 않음
            contentType: false, // Content-Type 헤더 설정하지 않음 (자동 설정됨)
        }).done((res) => {
            // 사진 전송 성공시 이미지 변경
            let reader = new FileReader();
            
            reader.onload = (e) => {
            	$("#userProfileImage").attr("src", e.target.result);
            }
            
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        }).fail((err) => {
            console.error(err);});
        */
	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






