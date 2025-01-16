package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	private final UserRepository userRepository; // 받아온 회원정보 담음
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest); // loadUser:userRequest 데이터 파싱 처리해줌.
		System.out.println(oauth2User.getAttributes()); // 받아온 회원정보 출력
		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		
		String username = "facebook_" + (String) userInfo.get("id"); // 중복되지 않아야함+페이스북으로 받은 정보이므로 facebook_을 붙여줌
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 패스워드로 로그인할게 아니므로 몰라도 되는 값
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");
		
		// 최초 로그인만 회원가입을 진행함
		// 이후 로그인은 회원가입 진행할 필요x (insert 되면 안됨) -> 회원정보 있는지 체크필요!!
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { // 페이스북 최초 로그인
			User user = User.builder()
					.username(username)
					.password(null)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes()); // 페이스북 로그인 구분하기 위해 principalDetails 객체의 두번째 생성자 사용
		}else { // 페이스북으로 이미 회원가입이 되어 있다는 뜻
			return new PrincipalDetails(userEntity, oauth2User.getAttributes()); // 페이스북 로그인 구분하기 위해 principalDetails 객체의 두번째 생성자 사용
		}
	}
}
