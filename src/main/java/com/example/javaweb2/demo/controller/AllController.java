package com.example.javaweb2.demo.controller;

import com.example.javaweb2.demo.entity.*;
import com.example.javaweb2.demo.repository.*;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class AllController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ViewsRepository viewsRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    private Email email;


    /**
     * 添加用户
     * @param user
     * @param session
     * @param modelMap
     * @return
     */
    @PostMapping("/addUser")
    public String  insertUser(@ModelAttribute User user, HttpSession session, ModelMap modelMap){
        List<User> users = userRepository.findAll();       // 遍历用户表
        int i = 0;
        // 当发现注册的用户名已经存在的时候，要求用户重新注册
        while(i<users.size()){
            if(users.get(i).getName().equals(user.getName())){
                modelMap.addAttribute("rename","rename");
                return "register";
            }
            else i++;
        }
        // 注册成功时将新用户信息加入用户表
        userRepository.save(user);
        session.setAttribute("registerSuccess", "注册新用户成功！");
        // 返回到登录页面
        return "redirect:/login.html";
    }

    /**
     * 登录页面
     * @param request
     * @param modelMap
     * @return
     */
    @GetMapping("/login.html")
    public String login(HttpServletRequest request, ModelMap modelMap){
        // 记录用户是否已经登录
        Object old_user = request.getSession().getAttribute("loginUser");
        // 记录用于注册成功与否
        Object register_success = request.getSession().getAttribute("registerSuccess");
        // 记录用户登录是否成功
        Object login_failed = request.getSession().getAttribute("loginFailed");
        // 记录用户登录状态
        Object no_login = request.getSession().getAttribute("no-login");
        if(old_user!=null){ request.getSession().removeAttribute("loginUser"); }
        if(register_success!=null){
            request.getSession().removeAttribute("registerSuccess");
            modelMap.addAttribute("message", "注册新用户成功！请登录！");
        }
        if(login_failed!=null){
            request.getSession().removeAttribute("loginFailed");
            modelMap.addAttribute("message", "用户名或密码错误！请重新输入！");
        }
        if(no_login!=null){
            request.getSession().removeAttribute("no-login");
            modelMap.addAttribute("message", "您尚未登录，请先登录！");
        }
        return "login";
    }

    /**
     * 登录操作
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/log_in")
    public String systemLogin(User user, HttpSession session){
        List<User> users = userRepository.findAll();
        int j = 0;
        int loginNum = 0;
        while(j<users.size()){
            if((user.getName().equals(users.get(j).getName()) || user.getName().equals(users.get(j).getEmail()))
                    && user.getPassword().equals(users.get(j).getPassword())){
                loginNum = 1;
                break;
            }
            else j++;
        }
        if(user.getName().equals("admin")){
            session.setAttribute("loginUser", users.get(j).getName());
            return "redirect:/sales.html";
        }
        else if(loginNum == 1){
            session.setAttribute("loginUser", users.get(j).getName());
            return "redirect:/index.html";
        }
        else{
            session.setAttribute("loginFailed", "登录失败！");
            return "redirect:/login.html";
        }
    }

    /**
     * 退出系统
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        Object user = request.getSession().getAttribute("loginUser");
        if (user!=null){
            session.removeAttribute("loginUser");
            session.setAttribute("no_login","您已退出，请重新登录!");
            modelMap.addAttribute("message", "您已退出，请重新登录!");
            return "redirect:/login.html";
        }
        return "redirect:/login.html";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/register.html")
    public String register(){
        return "register";
    }

    /**
     * 定向到首页
     * @return
     */
    @GetMapping("/")
    public String setIndex(){ return "redirect:/index.html"; }

    @GetMapping("/index.html")
    public String index(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        List<Goods> goods = goodsRepository.findAll();
        Object user = request.getSession().getAttribute("loginUser");
        Object name = request.getSession().getAttribute("name");
        Object jump = request.getSession().getAttribute("jump");
        Object limit = request.getSession().getAttribute("limit");
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else{
            modelMap.addAttribute("WelcomeUser", user.toString());
            if(name==null) modelMap.addAttribute("goods", goods);
            else{
                request.getSession().removeAttribute("name");
                int i = 0;
                while(i<goods.size()){
                    if(goods.get(i).getName().equals(name.toString())) System.out.println("匹配成功");
                    else goods.remove(i);
                    i++;
                }
                modelMap.addAttribute("goods", goods);
            }
            if(jump!=null){
                modelMap.addAttribute("jumpList", "jumpList");
                request.getSession().removeAttribute("jump");
            }
            if(limit!=null){
                modelMap.addAttribute("limit", "limit");
                request.getSession().removeAttribute("limit");
            }
            return "index";
        }
    }

    /**
     * 购物车页面
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @GetMapping("/cart.html")
    public String cart(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        List<Cart> carts = cartRepository.findAll();
        Object user = request.getSession().getAttribute("loginUser");
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else {
            modelMap.addAttribute("WelcomeUser", user.toString());
            modelMap.addAttribute("carts",carts);
            // 计算该用户购物车中商品件数
            modelMap.addAttribute("listNum", carts.size());
            int i = 0;
            int totalPrice = 0;
            while(i<carts.size()){
                // 计算商品价格
                totalPrice = totalPrice + carts.get(i).getTotal();
                i++;
            }
            modelMap.addAttribute("totalPrice", totalPrice);
        }
        return "cart";
    }

    /**
     * 添加购物车操作
     * @param user_name
     * @param good_name
     * @param price
     * @return
     */
    @GetMapping("/add_cart")
    public String  add_cart(@RequestParam("user_name") String user_name, @RequestParam("good_name") String good_name, @RequestParam("price") Integer price ){
        List<Cart> carts = cartRepository.findByGoodsNameAndUserName(good_name, user_name);
        if(carts.size()==0){
            Cart new_cart = new Cart();
            new_cart.setGoodsName(good_name);
            new_cart.setUserName(user_name);
            new_cart.setPrice(price);
            new_cart.setNum(1);
            new_cart.setTotal(price);
            cartRepository.save(new_cart);
        }
        else {
            int num = carts.get(0).getNum();
            int one_price = carts.get(0).getPrice();
            carts.get(0).setNum(num+1);
            carts.get(0).setTotal(carts.get(0).getTotal()+one_price);
            cartRepository.save(carts.get(0));
        }
        return "redirect:/cart.html";
    }

    /**
     * 删除购物车操作
     * @param id
     * @return
     */
    @GetMapping("/carts_delete")
    public String delete(Integer id){
        cartRepository.deleteById(id);
        return "redirect:/cart.html";
    }

    /**
     * 支付操作
     * @param user_name
     * @return
     */
    @GetMapping("/pay")
    public String pay(@RequestParam("name") String user_name){
        List<Cart> carts = cartRepository.findByUserName(user_name);
        int i = 0;
        while(i<carts.size()){
            Buy buy = new Buy();
            buy.setUserName(carts.get(i).getUserName());
            buy.setGoodsName(carts.get(i).getGoodsName());
            buy.setNum(carts.get(i).getNum());
            buy.setPrice(carts.get(i).getPrice());
            buy.setTotal(carts.get(i).getTotal());
            buy.setState("未发货");
            buyRepository.save(buy);
            cartRepository.delete(carts.get(i));
            i++;
        }
        return "redirect:/cart.html";
    }

    /**
     * 后台商品管理页面
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @GetMapping("/forms.html")
    public String form(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        List<Goods> goods = goodsRepository.findAll();
        Object user = request.getSession().getAttribute("loginUser");
        Object revise = request.getSession().getAttribute("change");
        Object add_goods = request.getSession().getAttribute("add_goods");
        Object goods_delete = request.getSession().getAttribute("goods_delete");
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else{
            if(user.toString().equals("admin")){
                modelMap.addAttribute("WelcomeUser", user.toString());
                modelMap.addAttribute("goods", goods);
                if (revise!=null){
                    modelMap.addAttribute("change", "change");
                    request.getSession().removeAttribute("change");
                }
                if (add_goods!=null){
                    modelMap.addAttribute("add_goods", "add_goods");
                    request.getSession().removeAttribute("add_goods");
                }
                if (goods_delete!=null){
                    modelMap.addAttribute("goods_delete", "goods_delete");
                    request.getSession().removeAttribute("goods_delete");
                }
                return "forms";
            }
            else {
                session.setAttribute("limit", "limit");
                return "redirect:/index.html";
            }
        }
    }

    /**
     * 后台订单管理页面
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @GetMapping("/Buyrecords.html")
    public String Buyrecords(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        Object user = request.getSession().getAttribute("loginUser");
        List<Buy> buys = buyRepository.findAll();
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else{
            if(user.toString().equals("admin")){
                modelMap.addAttribute("WelcomeUser", user.toString());
                modelMap.addAttribute("buys", buys);
                return "Buyrecords";
            }else {
                session.setAttribute("limit", "limit");
                return "redirect:/index.html";
            }
        }
    }

    /**
     * 后台浏览日志页面
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @GetMapping("/Viewsrecords.html")
    public String Viewsrecords(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        Object user = request.getSession().getAttribute("loginUser");
        List<Views> views = viewsRepository.findAll();
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else{
            if(user.toString().equals("admin")){
            modelMap.addAttribute("WelcomeUser", user.toString());
            modelMap.addAttribute("views", views);
            return "Viewsrecords";
            }else {
                session.setAttribute("limit", "limit");
                return "redirect:/index.html";
            }
        }
    }

    /**
     * 后台销售统计页面
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @GetMapping("/sales.html")
    public String sales(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        Object user = request.getSession().getAttribute("loginUser");
        if(user==null){
            session.setAttribute("no-login", "请先登录！");
            return "redirect:/login.html";
        }
        else {
            if(user.toString().equals("admin")){
                modelMap.addAttribute("WelcomeUser", user.toString());
                ArrayList name_list = new ArrayList();
                ArrayList num_list = new ArrayList();
                List<Goods> goods = goodsRepository.findAll();
                List<Buy> buys = buyRepository.findAll();
                int i = 0;
                while(i<goods.size()){
                    name_list.add(goods.get(i).getName());
                    int count = 0;
                    int j = 0;
                    while(j<buys.size()){
                        if(goods.get(i).getName().equals(buys.get(j).getGoodsName())) count = count + buys.get(j).getNum();
                        j++;
                    }
                    num_list.add(count);
                    i++;
                }

                modelMap.addAttribute("nameList", name_list);
                modelMap.addAttribute("numList", num_list);
                return "sales";
            }else {
                session.setAttribute("limit", "limit");
                return "redirect:/index.html";
            }
        }
    }

    /**
     * 新增浏览记录
     * @param user_name
     * @param good_name
     * @param modelMap
     * @return
     */
    @GetMapping("/addViews")
    public String addViews(@RequestParam("user") String user_name, @RequestParam("good_name") String good_name, ModelMap modelMap){
        Views views = new Views();
        views.setUserName(user_name);
        views.setGoodsName(good_name);
        views.setTime(new Date());
        viewsRepository.save(views);
        List<Goods> goods = goodsRepository.findByName(good_name);
        modelMap.addAttribute("goods", goods);
        modelMap.addAttribute("WelcomeUser", user_name);
        return "details";
    }

    /**
     * 删除浏览记录
     * @param id
     * @return
     */
    @GetMapping("/delete_view")
    public String delete_view(Integer id){
        viewsRepository.deleteById(id);
        return "redirect:/Viewsrecords.html";
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @GetMapping("/delete_buy")
    public String delete_buy(Integer id){
        buyRepository.deleteById(id);
        return "redirect:/Buyrecords.html";
    }

    /**
     * 后台删除商品
     * @param session
     * @param id
     * @return
     */
    @GetMapping("/goods_delete")
    public String delete(HttpSession session,Integer id){
        session.setAttribute("goods_delete","goods_delete");
        goodsRepository.deleteById(id);
        return "redirect:/forms.html";
    }

    /**
     * 后台添加商品
     * @param session
     * @param goods
     * @return
     */
    @PostMapping("/add_goods")
    public String  insertUser(HttpSession session,@ModelAttribute Goods goods){

        goodsRepository.save(goods);
        session.setAttribute("add_goods","add_goods");
        return "redirect:/forms.html";
    }

    /**
     * 后台修改商品
     * @param session
     * @param name
     * @param price
     * @param activity
     * @param detail
     * @return
     */
    @PostMapping("/change_goods")
    public String reviseGoods(HttpSession session, @RequestParam("name") String name, @RequestParam("price") String price,
                              @RequestParam("activity") String activity, @RequestParam("detail") String detail){
        List<Goods> goods = goodsRepository.findByName(name);
        if(!price.equals("default")) goods.get(0).setPrice(Integer.parseInt(price));
        if(!activity.equals("default")) goods.get(0).setActivity(activity);
        if(!detail.equals("default")) goods.get(0).setDetail(detail);
        goodsRepository.save(goods.get(0));
        session.setAttribute("change", "change");
        return "redirect:/forms.html";
    }

    /**
     * 后台发货
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/sendGoods")
    public String sendGoods(Integer id, HttpSession session){
        List<Buy> buys = buyRepository.findAll();

        int i = 0;
        while (i<buys.size()){
            if(buys.get(i).getId().intValue()==id) break;
            i++;
        }
        buys.get(i).setState("已发货");
        buyRepository.save(buys.get(i));
        List<User> users = userRepository.findByName(buys.get(i).getUserName());
        StringBuilder builder = new StringBuilder();
        String avtivecode = RandomStringUtils.randomAlphanumeric(15);
        Calendar now = Calendar.getInstance();
        builder.append("致   ");
        builder.append(users.get(0).getName()+":");
        builder.append("\n感谢您于  ");
        builder.append(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE));
        builder.append("\n购买了  ");
        builder.append(buys.get(i).getGoodsName());
        builder.append("\n数量： ");
        builder.append(buys.get(i).getNum());
        builder.append("\n总价： ");
        builder.append(buys.get(i).getTotal());
        builder.append("\n软件激活码： ");
        builder.append(avtivecode);
        builder.append("\n请尽快使用！");
        String sender="763656642@qq.com";
        String receiver = users.get(0).getEmail();
        String title="[荔枝数码]您购买的软件已发放激活码，请查收！     发件人：郭智伦";
        String text = builder.toString();
        try{
            email.send(sender, receiver, title, text);
            System.out.println("邮件发送成功！");
        } catch (Exception e){
            System.out.println("邮件发送失败！");
        }
        session.setAttribute("send","send");
        return "redirect:/Buyrecords.html";
    }
}
