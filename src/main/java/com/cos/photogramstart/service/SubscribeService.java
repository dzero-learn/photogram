package com.cos.photogramstart.service;

import javax.persistence.Query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.profileImageUrl, u.username, ");
		sb.append("if((SELECT 1 from subscribe where fromUserId=? AND toUserId = u.id),1,0) AS subscribeState, ");
		sb.append("if(u.id = ?, 1, 0) AS ownerState ");
		sb.append("FROM user u ");
		sb.append("INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");
		
		// 쿼리문 삽입
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId)
				;
		
		// 쿼리 실행
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> list = result.list(query, SubscribeDto.class); // 쿼리 조회 결과 dto에 받음
		
		return list;
	}
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
