package com.blackfat.open.platform.controller;

import com.blackfat.open.platform.domain.User;
import com.blacktfat.base.service.IHelloWorldService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/6/21-14:31
 */
@Controller
@RequestMapping(value="/users")
@Api(value = "/users", description = "用户管理")
public class UserController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @Autowired
    private IHelloWorldService helloService;


    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value="/" , method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        helloService.helloWorld();
        List<User> list = new ArrayList<User>(users.values());
        return list;
    }


    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody
    public String postUser(@ModelAttribute User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable Long id){
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }


    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        users.remove(id);
        return "success";
    }
}
