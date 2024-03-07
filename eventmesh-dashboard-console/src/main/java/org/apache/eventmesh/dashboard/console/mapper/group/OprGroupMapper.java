/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.dashboard.console.mapper.group;

import org.apache.eventmesh.dashboard.console.entity.GroupEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * operate Group mapper
 **/
@Mapper
public interface OprGroupMapper {

    @Insert("INSERT INTO `group` (cluster_id, name, member_count, members, type, state)"
        + "VALUE (#{clusterId},#{name},#{memberCount},#{members},#{type},#{state}) "
        + "ON DUPLICATE KEY UPDATE is_delete=0")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addGroup(GroupEntity groupEntity);

    @Update("UPDATE `group` SET member_count=#{memberCount},"
        + "members=#{members},type=#{type},state=#{state} WHERE id=#{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer updateGroup(GroupEntity groupEntity);

    @Delete("UPDATE `group` SET  is_delete=1 WHERE id=#{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer deleteGroup(GroupEntity groupEntity);

    @Select("SELECT * FROM `group` WHERE cluster_id=#{clusterId} AND name=#{name} AND is_delete=0")
    GroupEntity selectGroupByUnique(GroupEntity groupEntity);

    @Select("SELECT * FROM `group` WHERE id=#{id} AND is_delete=0")
    GroupEntity selectGroupById(GroupEntity groupEntity);

    @Select({
        "<script>",
        "   SELECT * FROM `group`",
        "   <where>",
        "       <if test='clusterId != null'>",
        "           cluster_id=#{clusterId}",
        "       </if>",
        "       <if test='name != null'>",
        "           AND name LIKE concat('%',#{name},'%')",
        "       </if>",
        "       AND is_delete=0",
        "   </where>",
        "</script>"})
    List<GroupEntity> selectGroup(GroupEntity groupEntity);

}
