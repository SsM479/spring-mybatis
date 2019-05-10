package com.itheima.springmvc.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.springmvc.exception.MessageException;
import com.itheima.springmvc.pojo.Items;
import com.itheima.springmvc.pojo.QueryVo;
import com.itheima.springmvc.service.ItemService;

@Controller
//@RequestMapping(value="/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	//入门程序
	/**
	 * 1.ModelAndView 无敌的 携带数据，返回视图路径
	 * 2.String 返回视图路径
	 * 	Model model：携带数据 model.addAttribute(arg0, arg1);  官方推荐此种 解耦：视图数据分离
	 * 3.void
	 * 	Model model,HttpServletRequest request, response
	 * 	model.addAttribute
	 * 	request.getRequestDispatcher("").forward(request, response)
	 * 	ajax异步请求合适 json格式数据
	 * @return
	 * @throws MessageException 
	 */
	@RequestMapping(value= {"/item/itemlist.action", "/item/itemlisthaha.action"})
	public String itemList(Model model) throws MessageException {
		
//		Integer i = 1/0;
		//从Mysql中查询
		List<Items> list = itemService.selectItemsList();	
		/*if(null == null) {
			throw new MessageException("商品信息不能为空");
		}*/
		model.addAttribute("itemList", list);
		return "itemList";
	}
	
	//去修改页面入参id
	@RequestMapping(value="/itemEdit.action")
//	public ModelAndView toEdit(@RequestParam(value="id", required=false, defaultValue="1") Integer idd, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
	public ModelAndView toEdit(Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		//Servlet时代开发
//		String id = request.getParameter("id");
		//查询一个商品
//		Items items = itemService.selectItemsById(Integer.parseInt(id));
		Items items = itemService.selectItemsById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("item", items);
		mav.setViewName("editItem");
		return mav;
	}
	
	//提交修改页面 入参  为 Items对象  
		@RequestMapping(value = "/updateitem.action")
//		public ModelAndView updateitem(Items items){
		public String updateitem(QueryVo vo,MultipartFile pictureFile) throws Exception{

			//保存图片到 
			String name = UUID.randomUUID().toString().replaceAll("-", "");
			//jpg
			String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
			
			pictureFile.transferTo(new File("G:\\upload\\" + name + "." + ext));
			
			vo.getItems().setPic(name + "." + ext);
			//修改
			itemService.updateItems(vo.getItems());
			
//			ModelAndView mav = new ModelAndView();
//			mav.setViewName("success");
			return "redirect:/itemEdit.action?id=" + vo.getItems().getId();
//			return "forward:/item/itemlist.action";
			
		}
	
	//删除多个
	@RequestMapping(value="/deletes.action")
	public ModelAndView deletes(QueryVo vo) {
		
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("success");
		return mav;
	}
	
	//修改
	@RequestMapping(value="/updates.action", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updates(QueryVo vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("success");
		return mav;
	}
	
	//json数据交互
	@RequestMapping(value="/json.action", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Items json(@RequestBody Items items) {
		
//		System.out.println(items);
		return items;
	}
	
	//restFul风格的开发
	@RequestMapping(value="/itemEdit/{id}.action")
	public ModelAndView toEdit1(@PathVariable Integer id,HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		Items items = itemService.selectItemsById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("item", items);
		mav.setViewName("editItem");
		return mav;
	}
	
}
