package com.study.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.base.model.PageParams;
import com.study.base.model.PageResult;
import com.study.content.mapper.CourseBaseMapper;
import com.study.content.mapper.CourseCategoryMapper;
import com.study.content.mapper.CourseMarketMapper;
import com.study.content.model.dto.AddCourseDto;
import com.study.content.model.dto.CourseBaseInfoDto;
import com.study.content.model.dto.QueryCourseParamsDto;
import com.study.content.model.po.CourseBase;
import com.study.content.model.po.CourseCategory;
import com.study.content.model.po.CourseMarket;
import com.study.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;


    /**
     * @Description:课程分页查询
     * @Author: kiligsmile
     * @Date: 2023/11/1 5:20 PM
     * @Param:
     * @param pageParams 分页查询参数
     * @param courseParamsDto 查询结果
     * @Return: 查询结果
    */
    @Transactional
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
//
//        // 拼装查询条件
//        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
//        // 根据名称模糊查询
//        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
//        // 根据课程审核状态查询
//        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
////        根据课程发布状态查询
//        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());
//        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
//        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
//        List<CourseBase> items = pageResult.getRecords();
//        long total = pageResult.getTotal();
//        PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(items,total,pageParams.getPageNo(), pageParams.getPageSize() );
//        return courseBasePageResult;

        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名称模糊查询,在sql中拼接 course_base.name like '%值%'
        queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
        //根据课程审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()), CourseBase::getAuditStatus,courseParamsDto.getAuditStatus());
        //todo:按课程发布状态查询

        //创建page分页参数对象，参数：当前页码，每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        //开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        //数据列表
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();

        //List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(items,total,pageParams.getPageNo(), pageParams.getPageSize());
        return  courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto ) {
//        参数的合法性校验
        //合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            throw new RuntimeException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }
//        向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
//        courseBaseNew.setName(dto.getName());
//        courseBaseNew.setName(dto.getDescription());
//        上面的从原始对象中get拿数据向新对象set,比较复杂和冗余
        BeanUtils.copyProperties(dto,courseBaseNew);  // 只要属性名称一致就可以拷贝，全拷贝
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
//        审核状态默认为未提交
        courseBaseNew.setAuditStatus("202002");
//        发布状态为未发布
        courseBaseNew.setStatus("203001");

//        插入数据库
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert<=0){
            throw new  RuntimeException("添加课程失败");
        }

//        向课程营销表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
//        将页面输入的数据拷贝到courseBaseNew
        BeanUtils.copyProperties(dto,courseMarketNew);
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        saveCourseMarket(courseMarketNew);

        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        return courseBaseInfo;
    }

//    单独写一个方法保存营销信息，逻辑：存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarketNew){
//        参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if(charge.isEmpty()){
            throw new RuntimeException("收费规则为空");
        }
//        如果课程收费，价格没有填写也需要抛出异常
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice()==null||courseMarketNew.getPrice()<=0){
                throw new RuntimeException("课程价格不能为空，且必须大于零！");
            }
        }

//        从数据库查询营销信息，存在则更新，不存在则添加
        CourseMarket courseMarket = courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarket==null){
//             插入数据库
            int insert = courseMarketMapper.insert(courseMarketNew);
            return insert;

        }else{
//            将courseMarketNew拷贝至courseMarket
            BeanUtils.copyProperties(courseMarketNew,courseMarket);
            courseMarket.setId(courseMarketNew.getId());
//            更新
            int i = courseMarketMapper.updateById(courseMarket);
            return i;

        }
    }

//    查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){
//        从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            return null;
        }
//        从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
//        组装在一起

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if(courseMarket!=null){
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

//        通过courseCategoryMapper查询分类信息，将分类名称放在courseBaseInfoDto对象中
        CourseCategory courseCategoryBySt  = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt  = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
    }
}
