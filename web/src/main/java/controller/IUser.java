package controller;

import annotation.Mapper;
import annotation.Param;

/**
 * Created by blue on 2018/1/11.
 */
@Mapper
public interface IUser {
    void getAll(@Param("shopId") Long shopId,@Param("id") int id, @Param("name") String name);
}
