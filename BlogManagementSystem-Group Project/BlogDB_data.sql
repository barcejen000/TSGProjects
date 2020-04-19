use blogmanagementsystemdb;

insert into user(username, `password`, enabled)
	values
	('admin', 'password', true),
    ('user', 'password', true);
    
insert into `role`(`role`)
	values
    ('ROLE_ADMIN'),
    ('ROLE_USER');

insert into user_role(username, roleId)
	values
    ('admin', 1),
    ('admin', 2),
    ('user', 2);
    
    update user SET `password` = '$2a$10$Ax0LxspM/nf93Po5PVUuk.5ty91ldtSpFDFBkDrnjbqng8aVSe/J6' where username = 'admin';

	update user set `password` ='$2a$10$Ax0LxspM/nf93Po5PVUuk.5ty91ldtSpFDFBkDrnjbqng8aVSe/J6' where username = 'user';

insert into hashtag(`name`)
 values('fit'),('healthy'),('vegan');
 
 insert into article(postDate, title, content, approved, username, static)
	values
	('2020-04-10 19:36:00', 'Triple Threat Tarts', '<p><img src="https://lh3.googleusercontent.com/PGvL_ZROSSasuBp5DiLEhZm0vXNyfzZd5yf-q4Jv7QTvOoZ2S7yYYSfRktK5AGXLpQ4OFioPN1-NCrQnIs9xgkagTNLyxxuV0a31L5MFZYCMS-7nNwXGWbdtYvXGdhvWAqmjkHgkq89Sch7ltHervyot8pxhus9MKuIw1XDAHhbxyLFhN1FFnvY2zfh7qpaptPVo7cMIfvWA2qjsgWb2icSMX3N7b24wK_oBv560oh7XCSjSZiNJeUyjO4ZaZqooYjwKFf0XXF9Ln0FjTchYOnoQi82BO98ybfBG2e5UUP6iqguLNT8K1brAMh-dDb7266OlxrUsPvEbS6ukEQW5vpRWA9-4GNWRP5M42G8e99cy2ihe0oa3C0RoXlLOfCeydG7TM9QcJv5BlWc4x3y0TGRA34s6ahMCJDXwcUUvm1KiA1zws7GMSGV_fYM-v5Dg0oc08TcqLJTonD_bCkDnupsd2AXuQfeL1noq0DJyLi2mxhy74hqTxz_f1Xeaap6pQ5I_k2exPOHKwcMW3fVlZGedvwTFzXVq0YHt0JpeTy1gysYvwlOWL2-NWTvy30G_vu4SOAmjGFfAWLs8o-SqSJdt_xUQf00ItD0sIQ6Kjg-Gu6UXKhvhklT-RlCfTdl4t0P0f-YTihgDgb9AzxOc5nkQq7iBrGiJinzpfE-sig_ak8ofBzOkwUX8iyK_ZA=w1010-h757-no" alt="3 tarts" width="400" height="300" /></p>
	<p>Raspberry cream, Chocolate pistachio, White chocolate raspberry</p>', 4, 'user', 0),
	('2020-04-16 08:35:00','Vegan(almost) Ramon Noodles', '<p><img src="https://lh3.googleusercontent.com/HrPHFyzpp7kTkRpoSyXlmF8IC8i7E3H8rjVDJhyfulEcta_9rr1m3Pxe-ISclb_H7hS8GpyOpOhx99fbGnqir2ASZVPngrWGInYozgnjGL1ybMo_z_krkEVt2999q3zKIDgsJOuI9_aWLY6HracVZU97u0S9j5U9fbOP-smtx3JhTznWXuxKnoLUG-glL8j-fy32Yfet49wLaFGAc2_PmGVNx3PWEFPlKtv1ASQTKR6xsqT-2ZntQCsY36J0IW-Kt2uI2EK7lkRc8P1WjoRO4GQ_wwaybncGWyFOojxvVQR_Zq754Ad5edz-RjzJ34r3z0G6JxuIU5JBalCfCjNfFAr5fI-5FdzFLajDmt1avRGVRPMYU31sL4Vw7UV2VftcmkMbZgcFodNx7S3Ifq-EWB6tEJhpVOyyFTMGBealwjwxlQIkZTBnIah2T9x5LspOedEjUOW1xvQaYJ3HCTvTsZUa-4uULTFYj7eQYfIA8MbfGARCR5wlwouRkB5j391SF-FvtDApqjAr0fYTzj1ndxO_lts43yjyNPd0XdPEAPRwooztzq7F9FecyDgChRh_LUGhVKzt0GhEB8nBff71l1Gbe3RQeXqvgxQ-_tNFJSNXS70CNiuzx_L0bcn-q7ylz_vC0Gqbjl0YUvNpSFmbiCSRU-zxvoR2-7g6r9E6yi51SrZ0swmaqxaXwVUWAw=w1010-h757-no" alt="noodles" width="300" height="225" /></p>
	<p>&nbsp;</p>
	<p>These noodles are quick and easy to make. Made with fresh ingredients and packed with awesome flavor. You will never go back to the packets.</p>', 2, 'admin', 0);
 
insert into comment(`title`, body, articleId)
	values
    ('Oh nice', 'look there', 1);