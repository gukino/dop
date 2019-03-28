package com.clsaa.dop.server.image.feign;

import com.clsaa.dop.server.image.config.FeignConfig;
import com.clsaa.dop.server.image.model.po.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     ProjectFeign,用于调用harbor的project接口
 * </p>
 * @author xzt
 * @since 2019-3-24
 */
@Component
@FeignClient(url = "${feign.url}",name = "project",configuration = FeignConfig.class)
public interface ProjectFeign {

    /**
     *  <p>
     *      使用Feign来连接harbor api
     *  </p>
     * @param name 项目名称
     * @param publicStatus 项目类型
     * @param owner 项目创建者
     * @param page 按照几页返回
     * @param pageSize 每页的大小
     * @return {@link List<Project>} 项目的列表类型
     */
    @GetMapping(value = "/projects")
    List<Project> projectsGet(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "public", required = false) Boolean publicStatus,
                              @RequestParam(value = "owner", required = false) String owner,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "page_size", required = false) Integer pageSize,
                              @RequestHeader(value = "Authorization") String auth);

    /**
     * 根据项目id返回 Project
     * @param projectId 项目id
     * @return {@link Project} 项目信息
     */
    @GetMapping(value = "/projects/{project_id}")
    Project projectsProjectIdGet(@PathVariable("project_id") Long projectId);

    /**
     * 新建项目
     * @param project 传入的项目的基本信息
     */
    @PostMapping(value = "/projects")
    void projectsPost(@RequestBody ProjectReq project);

    /**
     * 通过项目id来更新项目信息
     * @param projectId 项目id
     * @param project 项目内容
     */
    @PutMapping(value = "/projects/{project_id}")
    void projectsProjectIdPut(@PathVariable("project_id") Long projectId,
                              @RequestBody ProjectReq project);

    /**
     * 通过项目id删除项目
     * @param projectId 项目id
     */
    @DeleteMapping(value = "/projects/{project_id}")
    void projectsProjectIdDelete(@PathVariable("project_id") Long projectId);

    /**
     * 通过项目id获取项目的操作日志
     * @param projectId 项目id
     * @param username 用户名
     * @param repository 仓库
     * @param tag 标签
     * @param operation 操作
     * @param beginTimestamp 开始
     * @param endTimestamp 结束
     * @param page 第几页
     * @param pageSize 每页包含数据条目数
     * @return 对于项目的访问日志
     */
    @GetMapping(value = "/projects/{project_id}/logs")
    List<AccessLog> projectsProjectIdLogsGet(@PathVariable("project_id") Long projectId,
                                             @RequestParam(value = "username", required = false) String username,
                                             @RequestParam(value = "repository", required = false) String repository,
                                             @RequestParam(value = "tag", required = false) String tag,
                                             @RequestParam(value = "operation", required = false) String operation,
                                             @RequestParam(value = "begin_timestamp", required = false) String beginTimestamp,
                                             @RequestParam(value = "end_timestamp", required = false) String endTimestamp,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "page_size", required = false) Integer pageSize);

    /**
     * 通过项目id获取项目的matadatas
     * @param projectId
     * @return {@link ProjectMetadata} 项目的基本信息
     */
    @GetMapping(value = "/projects/{project_id}/metadatas")
    ProjectMetadata projectsProjectIdMetadatasGet(@PathVariable("project_id") Long projectId);
    /**
     * 为项目添加metadata信息
     * @param projectId 项目id
     */
    @PostMapping(value = "/projects/{project_id}/metadatas")
    void projectsProjectIdMetadatasPost(@PathVariable("project_id") Long projectId,
                                        @RequestBody ProjectMetadata metadata);

    /**
     * 删除Projectmetadas的某个具体属性
     * @param projectId 项目id
     * @param metaName 属性名称
     */
    @DeleteMapping(value = "/projects/{project_id}/metadatas/{meta_name}")
    void projectsProjectIdMetadatasMetaNameDelete(@PathVariable("project_id") Long projectId,
                                                  @PathVariable("meta_name") String metaName);

    /**
     * 通过项目id 和meta_name获得项目的metadata信息
     * @param projectId 项目id
     * @param metaName meta名
     * @return 项目的meta信息
     */
    @GetMapping(value = "/projects/{project_id}/metadatas/{meta_name}")
    ProjectMetadata projectsProjectIdMetadatasMetaNameGet(@PathVariable("project_id") Long projectId,
                                                          @PathVariable("meta_name") String metaName);

    /**
     *  获取项目的参与人员
     * @param projectId 项目id
     * @param entityname 项目成员名称
     * @return {@link List<ProjectMemberEntity>} 项目人员的列表
     */
    @GetMapping(value = "/projects/{project_id}/members")
    List<ProjectMemberEntity> projectsProjectIdMembersGet(@PathVariable("project_id") Long projectId,
                                                          @RequestParam(value = "entityname", required = false) String entityname);

    /**
     * 为项目中添加成员
     * @param projectId 项目id
     * @param projectMember 项目成员实体
     */
    @PostMapping(value = "/projects/{project_id}/members")
    void  projectsProjectIdMembersPost(@PathVariable("project_id") Long projectId,
                                       @RequestBody ProjectMember projectMember);
    /**
     * 从项目中删除 项目成员
     * @param projectId 项目id
     * @param mid 成员的id
     */
    @DeleteMapping(value = "/projects/{project_id}/members/{m_id}")
    void projectsProjectIdMembersMidDelete( @PathVariable("project_id") Long projectId,
                                            @PathVariable("m_id") Long mid);

    /**
     * 通过项目id和成员id来获取项目成员的信息
     * @param projectId 项目id
     * @param mid 项目成员的id
     * @return {@link ProjectMemberEntity} 项目成员的信息
     */
    @GetMapping(value = "/projects/{project_id}/members/{m_id}")
    ProjectMemberEntity projectsProjectIdMembersMidGet(@PathVariable("project_id") Long projectId,
                                                       @PathVariable("m_id") Long mid);

    /**
     * 通过项目id和项目成员id来修改项目成员的角色
     * @param projectId 项目id
     * @param mid 项目成员id
     * @param role 修改用户在项目中的角色
     */
    @PutMapping(value = "/projects/{project_id}/members/{m_id}")
    void projectsProjectIdMembersMidPut(@PathVariable("project_id") Long projectId,
                                        @PathVariable("m_id") Long mid,
                                        @RequestBody RoleRequest role);

}
