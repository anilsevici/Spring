package com.obss.three.web.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.obss.three.users.model.User;
import com.obss.three.users.service.JasperDatasourceService;
import com.obss.three.users.service.UserService;
import com.obss.three.validator.UserRegisterValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JasperDatasourceService jasperService;

	@Autowired
	private UserRegisterValidator userValidator;

	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public String listUsers(Model model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "List-users";
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String printreport() {

		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(
				Columns.column("Username", "username", DataTypes.stringType()),
				Columns.column("Email", "email", DataTypes.stringType()),
				Columns.column("Birthday", "birthDay", DataTypes.dateType()),
				Columns.column("Sex", "sex", DataTypes.byteType()))
				.title(// title of the report
				Components.text("User Report").setHorizontalAlignment(
						HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())
				.setDataSource(jasperService.getDataSource());

		try {
			// show the report
			report.show();

			// export the report to a pdf file
			report.toPdf(new FileOutputStream(
					"C:/Users/ANIL/Desktop/report.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "redirect:/userlist";

	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String newAccount(Model model) {
		model.addAttribute("userForm", new User());
		return "registration";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.saveUser(userForm);

		return "redirect:/loginPage";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "OBSS HOME PAGE");
		model.addObject("message", "WELCOME");
		model.setViewName("homePage");
		return model;
	}

	@RequestMapping(value = { "/loginPage" }, method = RequestMethod.GET)
	public ModelAndView loginPage(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out from OBSSDEV successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcomePage");
		return model;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(
				"yyyy-mm-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

}
