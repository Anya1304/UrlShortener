package com.linkgenerator.repository;

import com.linkgenerator.model.PassedUserInfo;
import org.springframework.data.redis.connection.RedisSetCommands;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassedUserInfoRepository extends RedisSetCommands {
}
