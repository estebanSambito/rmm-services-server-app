insert into rmm_user(us_id,us_username,us_password) values (1,'esalazar','esalazar');

insert into rmm_user(us_id,us_username,us_password) values (2,'dron','123123');

--devices
INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (1,4,'windows','DEV');
INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (2,4,'mac','DEV');

--services	
INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (3,5,'AV win','SERV');

INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (4,7,'AV mac','SERV');

INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (5,3,'cludberry','SERV');
INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (6,2,'PSA','SERV');
INSERT INTO rmm_product(
	pr_id,pr_cost, pr_name, pr_type)
	VALUES (7,1,'teamviewer','SERV');
	
/*INSERT INTO rmm_invoice(
	in_creation_date, in_status, in_total, fk_user)
	VALUES (now(), null, null, 1);
	
INSERT INTO rmm_detail(
	dt_id, dt_quantity, product_id, fk_invoice)
	VALUES (1,1, 1, 1);
	
INSERT INTO rmm_detail(
	dt_id, dt_quantity, product_id, fk_invoice)
	VALUES (2,1, 2, 1);	
	*/