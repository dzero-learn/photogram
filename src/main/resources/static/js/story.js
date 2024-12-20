/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

let page = 0;

$(document).ready(function(){
    storyLoad(page);
});

// (1) 스토리 로드하기
function storyLoad(page) {
    $.ajax({
        type: "POST",
        url: `/api/story?page=${page}`,
        dataType: "json"
    }).done(res => {
    	console.log("page",page);
        console.dir(res);
        res.data.content.forEach((e)=>{
            $("#storyList").append(getStoryItem(e));
        });
    }).fail(error => {
        console.dir(error);
    });
}

function getStoryItem(e) {
    let item = `<!--전체 리스트 아이템-->
                <div class="story-list__item"> 
                    <div class="sl__item__header">
                    	<div>
                    		<img class="profile-image" src="/upload/${e.user.profileImageUrl}"
                    			onerror="this.src='/images/person.jpeg'" />
                    	</div>
                    	<div>${e.user.username}</div>
                    </div>

                    <div class="sl__item__img">
                    	<img src="/upload/${e.postImageUrl}" />
                    </div>

                    <div class="sl__item__contents">
                    	<div class="sl__item__contents__icon">

                    		<button>`;

                    if(e.likeState) { // 좋아요 한 상태
                        item += `<i class="fas fa-heart active" id="storyLikeIcon-${e.id}" onclick="toggleLike(${e.id})"></i>`;
                    } else { // 좋아요 안 한 상태
                        item += `<i class="far fa-heart" id="storyLikeIcon-${e.id}" onclick="toggleLike(${e.id})"></i>`;
                    }

                  item += `</button>
                    	</div>

                    	<span class="like"><b id="storyLikeCount-${e.id}">${e.likeCount} </b>likes</span>

                    	<div class="sl__item__contents__content">
                    		<p>${e.caption}</p>
                    	</div>

                    	<div id="storyCommentList-${e.id}">`;

                  if(e.comments.length > 0) {
                      e.comments.forEach((comment) => {
                      item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
                              	<p>
                              		<b>${comment.user.name} :</b> ${comment.content}
                              	</p>

                              	<button onClick="deleteComment(${e.id},${comment.id})">
                              	    <i class="fas fa-times"></i>
                              	</button>

                              </div>`;
                      });
                  }

                  item += `</div>

                    	<div class="sl__item__input">
                    		<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${e.id}" />
                    		<button type="button" onClick="addComment(${e.id})">게시</button>
                    	</div>

                    </div>
                </div>`;

    return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	let checkLastPosition = $(document).height() - ($(window).scrollTop() + $(window).height());

	if(checkLastPosition < 1 && checkLastPosition > -1) {
		page++;
		storyLoad(page);
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);

	if (likeIcon.hasClass("far")) { // 좋아요
        $.ajax({
	        type: "POST",
            url: `/api/image/${imageId}/likes`,
            dataType: "json"
	    }).done(res=>{
	        let likeCount = $(`#storyLikeCount-${imageId}`).text();
	        $(`#storyLikeCount-${imageId}`).text(Number(likeCount) + 1);

	        likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");
	    }).fail(error=>{
	        console.dir(error);
	    });
	} else { // 좋아요취소
	    $.ajax({
            type: "DELETE",
               url: `/api/image/${imageId}/likes`,
               dataType: "json"
        }).done(res=>{
            let likeCount = $(`#storyLikeCount-${imageId}`).text();
        	$(`#storyLikeCount-${imageId}`).text(Number(likeCount) - 1);

            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");
        }).fail(error=>{
            console.dir(error);
        });
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
	    imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	//console.log(JSON.stringify(data));
	$.ajax({
	    type:"POST",
	    url:"/api/comment",
	    data: JSON.stringify(data),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json"
	}).done((res)=>{ // res는 항상 통신결과값이 담김
	    console.log("res:",res);
	    let comment = res.data;
        let content = `
            <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
                <p>
                    <b>${comment.user.name} :</b>
                    ${comment.content}
                </p>
                <button onClick="deleteComment(${comment.image.id},${comment.id})"><i class="fas fa-times"></i></button>
            </div>`;

        commentList.prepend(content);
	}).fail((err)=>{
	    console.error("Error:",err);
	});

	commentInput.val(""); // 댓글쓰기란 초기화(오류가 나더라도 빈값을 초기화)
}

// (5) 댓글 삭제
function deleteComment(imageId,commentId) {
    let data = {
        imageId : imageId,
        commentId : commentId
    };

    $.ajax({
    	    type:"DELETE",
    	    url:"/api/comment/",
    	    data: JSON.stringify(data),
    	    contentType: "application/json",
    	    dataType: "json"
    	}).done((res)=>{
    	    console.log("res:",res);
    	    $(`#storyCommentItem-${commentId}`).remove();
    	}).fail((err)=>{
    	    console.error("Error:",err);
    	});
}







