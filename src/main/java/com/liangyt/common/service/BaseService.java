package com.liangyt.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liangyt.common.entity.IDEntity;
import com.liangyt.common.repository.BaseRepository;
import com.liangyt.common.repository.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 描述：服务层基础类 <br/>
 * 基本上跟持久层差不多了，多了一个翻页处理的方法
 *
 * @author tony
 * @创建时间 2017-08-27 20:22
 */
@SuppressWarnings("all")
public abstract class BaseService<D extends BaseRepository<T>, T extends IDEntity> {

    @Autowired
    protected D d;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    @Transactional
    public int insert(T entity) {
        return this.d.insert(entity);
    }

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Transactional
    public int inserts(List<T> entities) {
        return this.d.inserts(entities);
    }

    /**
     * 根据主键更新
     * @param entity 带主键的更新内容体
     * @return
     */
    public int updateByPrimaryKey(T entity) {
        return this.d.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键查询实体
     * @param id 主键
     * @return
     */
    public T selectByPrimaryKey(String id) {
        return this.d.selectByPrimaryKey(id);
    }

    /**
     * 根据主键删除
     * @param id 主键
     */
    @Transactional
    public void deleteByPrimaryKey(String id) {
        this.d.deleteByPrimaryKey(id);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public T findOne(String id) {
        return this.d.findOne(id);
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public boolean exists(String id) {
        return this.d.exists(id);
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    public List<T> findAll() {
        return this.d.selectAll();
    }

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    public List<T> findAll(List<String> ids) {
        return this.d.selectAll(ids);
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    public long count() {
        return this.d.count();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Transactional
    public void delete(String id) {
        this.d.delete(id);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Transactional
    public void deleteByEntity(T entity) {
        this.d.deleteByEntity(entity);
    }

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    @Transactional
    public void deletes(List<? extends T> entities) {
        this.d.deletes(entities);
    }

    /**
     * Deletes all entities managed by the Service.
     */
    @Transactional
    public void deleteAll() {
        this.d.deleteAll();
    }

    public List<T> findByCondfig(Map<String, Object> config) {
        return this.d.findByCondfig(config);
    }

    /**
     * 根据条件分页查询
     * @param params 查询条件
     * @param pageable 分页条件
     * @return
     */
    public Pageable<T> findByCondfigWithPage(Map<String, Object> params, Pageable pageable) {
        if (params != null && pageable.getSort() != null) {
            params.put("sort", pageable.getSort());
        }

        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize());
        Page<T> page = (Page<T>)this.d.findByCondfig(params);

        pageable.setRows(page.getResult());
        pageable.setTotal(page.getTotal());

        return pageable;
    }
}
