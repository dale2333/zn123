package com.wut.zn.controller;

import com.wut.zn.entity.CarCategory;
import com.wut.zn.service.CarCategoryService;
import com.wut.zn.utils.Result;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CarCategoryController {
    @Autowired
    private CarCategoryService categoryService;

    @GetMapping("/paged")
    public Result<Object> getPaged(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String categoryName) { //参数名与 Service 一致
        PageInfo<CarCategory> pageInfo = categoryService.getPagedCategories(pageNo, pageSize, categoryName);
        return Result.success().data(pageInfo.getList()).count((int) pageInfo.getTotal());
    }

    @PostMapping("/add") //保持与前端请求路径一致
    public Result<?> add(@Valid @RequestBody CarCategory category) {
        categoryService.addCategory(category);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        categoryService.batchDelete(ids);
        return Result.success().message("删除成功，共删除 " + ids.size() + " 条记录");
    }

    @GetMapping("/{id}")
    public Result<CarCategory> getCategoryById(@PathVariable Long id) {
        CarCategory category = categoryService.getCategoryById(id);

        if (category != null) {
            //成功时直接构建 Result<CarCategory>
            return new Result<CarCategory>()
                    .code(0)
                    .message("success")
                    .data(category);
        } else {
            //失败时也构造 Result<CarCategory>，data 设为 null
            return new Result<CarCategory>()
                    .code(-1)
                    .message("分类不存在")
                    .data(null); // 明确泛型类型为 CarCategory，允许 data 为 null
        }
    }
    // 更新分类 
    @PutMapping("/{id}")
    public Result<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CarCategory category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return Result.success();
    }
}