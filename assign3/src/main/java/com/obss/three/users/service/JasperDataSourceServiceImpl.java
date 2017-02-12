package com.obss.three.users.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.obss.three.users.dao.UserDao;
import com.obss.three.users.model.JasperDTO;
import com.obss.three.users.model.User;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("jasperreportservice")
@Transactional
public class JasperDataSourceServiceImpl implements JasperDatasourceService {

	@Autowired
	private UserDao userDao;

	@Override
	public JRDataSource getDataSource() {
		List<User> records = userDao.findAllUsers();
		List<JasperDTO> dtos = new ArrayList<JasperDTO>();

		// Map records
		for (User user : records) {
			JasperDTO dto = new JasperDTO();
			dto.setUsername(user.getUsername());
			dto.setEmail(user.getEmail());
			dto.setBirthDay(user.getBirthDate());
			dto.setSex(user.getSex());

			dtos.add(dto);
		}
		// Return wrapped collection
		return new JRBeanCollectionDataSource(dtos);
	}

}
