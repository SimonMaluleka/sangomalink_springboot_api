package com.kgoro.sangoma_link.exception

import lombok.Data
import lombok.EqualsAndHashCode

@EqualsAndHashCode(callSuper = true)
@Data
class UserEmailNotFoundException(msg: String): Throwable()
