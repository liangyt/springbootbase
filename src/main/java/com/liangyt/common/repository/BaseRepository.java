package com.liangyt.common.repository;


import com.liangyt.common.entity.IDEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述：持久层基础类 <br/>
 * 这个基本类参考了 JPA 里面的基础类，基本是照搬过来的。
 *
 * @author tony
 * @创建时间 2017-08-27 20:24
 */
@SuppressWarnings("all")
public interface BaseRepository<T extends IDEntity> {
    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    int insert(T entity);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    int inserts(List<T> entities);

    /**
     * 根据主键更新
     * @param entity 带主键的更新内容体
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 根据主键查询实体
     * @param id 主键
     * @return
     */
    T selectByPrimaryKey(String id);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void deleteByPrimaryKey(String id);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(String id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(String id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> selectAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    List<T> selectAll(List<String> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(String id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void deleteByEntity(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    void deletes(List<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();

    /**
     * 根据条件查询
     * @param config
     * @return
     */
    List<T> findByCondfig(Map<String, Object> config);
}
