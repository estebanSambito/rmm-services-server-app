insert into rmm_user(us_username,us_password) values ('esalazar','esalazar');

insert into rmm_user(us_username,us_password) values ('dron','123123');

INSERT INTO rmm_product(
	pr_cost, pr_name, pr_type)
	VALUES (20,'windows','DEV');

INSERT INTO rmm_product(
	pr_cost, pr_name, pr_type)
	VALUES (20,'mac','DEV');
		
INSERT INTO rmm_invoice(
	in_creation_date, in_status, in_total, fk_user)
	VALUES (now(), null, null, 1);
	
INSERT INTO rmm_detail(
	dt_id, dt_quantity, product_id, fk_invoice)
	VALUES (1,1, 1, 1);
	
INSERT INTO rmm_detail(
	dt_id, dt_quantity, product_id, fk_invoice)
	VALUES (2,1, 2, 1);	