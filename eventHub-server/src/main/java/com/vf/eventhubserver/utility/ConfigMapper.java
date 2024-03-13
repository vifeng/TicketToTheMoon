package com.vf.eventhubserver.utility;

import org.mapstruct.Mapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Mapper(uses = {NonNull.class, Nullable.class})
public interface ConfigMapper {}
