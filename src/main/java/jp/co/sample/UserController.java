package jp.co.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.dao.UserMasterDao;
import jp.co.entity.UserMaster;
import jp.co.model.IndexForm;

@Controller
@EnableAutoConfiguration
public class UserController {
	/** 在庫情報用Dao */
	@Autowired
	private UserMasterDao userDao;

	/**
	 * 一覧画面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/", "/users" })
	public String index(Model model) {

		List<UserMaster> users = userDao.selectAll();

		model.addAttribute("users", users);
		return "home/index";
	}

	@RequestMapping(value = "/users/new")
	public String newUser(Model model) {
		model.addAttribute("indexForm", new IndexForm());
		return "home/new";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute IndexForm indexForm, Model model) {

		UserMaster user = new UserMaster();


		user.name=indexForm.getName();
		user.email=indexForm.getEmail();
		user.password=indexForm.getPassword();
		user.remarks=indexForm.getRemarks();

		userDao.insert(user);

		return "redirect:/users";
	}
}
