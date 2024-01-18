package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id;
	private String profileImageUrl;
	private String username;
	private int subscribeState;
	private int ownerState;
}
