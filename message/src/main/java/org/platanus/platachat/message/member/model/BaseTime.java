package org.platanus.platachat.message.member.model;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseTime {
	private LocalDateTime created;
	private LocalDateTime updated;
}