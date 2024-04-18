CREATE TABLE `cart` (
	`cart_id`	int	NOT NULL,
	`user_id`	varchar(50)	NOT NULL	COMMENT '아이디',
	`product_id`	int	NOT NULL,
	`amount`	int	NULL
);

CREATE TABLE `users` (
	`user_id`	varchar(50)	NOT NULL	COMMENT '아이디',
	`user_name`	varchar(50)	NOT NULL	COMMENT '이름',
	`user_password`	varchar(200)	NOT NULL	COMMENT 'mysql password 사용',
	`user_birth`	varchar(8)	NOT NULL	COMMENT '생년월일 : 19840503',
	`user_auth`	varchar(10)	NOT NULL	COMMENT '권한: ROLE_ADMIN,ROLE_USER',
	`user_point`	int	NOT NULL	COMMENT 'default : 1000000',
	`created_at`	datetime	NOT NULL	COMMENT '가입일자',
	`latest_login_at`	datetime	NULL	DEFAULT NULL	COMMENT '마지막 로그인 일자'
);

CREATE TABLE `user_address` (
	`address_id`	int	NOT NULL,
	`user_id`	varchar(50)	NOT NULL	COMMENT '아이디',
	`address_info`	text	NULL
);

CREATE TABLE `products` (
	`product_id`	int	NOT NULL,
	`product_name`	varchar(255)	NULL,
	`price`	decimal(10,2)	NULL,
	`stock`	bigint	NULL,
	`product_image`	text	NULL,
	`product_info`	varchar(30)	NULL,
	`register_date`	date	NULL,
	`latest_update_at`	date	NULL
);

CREATE TABLE `product_categories` (
	`product_id`	int	NOT NULL,
	`category_id`	int	NOT NULL
);

CREATE TABLE `categories` (
	`category_id`	int	NOT NULL,
	`category_name`	varchar(20)	NULL
);

CREATE TABLE `order` (
	`order_id`	int	NOT NULL,
	`user_id`	varchar(50)	NOT NULL	COMMENT '아이디',
	`order_date`	date	NULL,
	`Field`	date	NULL
);

ALTER TABLE `cart` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
	`cart_id`,
	`user_id`,
	`product_id`
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `user_address` ADD CONSTRAINT `PK_USER_ADDRESS` PRIMARY KEY (
	`address_id`,
	`user_id`
);

ALTER TABLE `products` ADD CONSTRAINT `PK_PRODUCTS` PRIMARY KEY (
	`product_id`
);

ALTER TABLE `product_categories` ADD CONSTRAINT `PK_PRODUCT_CATEGORIES` PRIMARY KEY (
	`product_id`,
	`category_id`
);

ALTER TABLE `categories` ADD CONSTRAINT `PK_CATEGORIES` PRIMARY KEY (
	`category_id`
);

ALTER TABLE `order` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
	`order_id`,
	`user_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_users_TO_cart_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_products_TO_cart_1` FOREIGN KEY (
	`product_id`
)
REFERENCES `products` (
	`product_id`
);

ALTER TABLE `user_address` ADD CONSTRAINT `FK_users_TO_user_address_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `product_categories` ADD CONSTRAINT `FK_products_TO_product_categories_1` FOREIGN KEY (
	`product_id`
)
REFERENCES `products` (
	`product_id`
);

ALTER TABLE `product_categories` ADD CONSTRAINT `FK_categories_TO_product_categories_1` FOREIGN KEY (
	`category_id`
)
REFERENCES `categories` (
	`category_id`
);

ALTER TABLE `order` ADD CONSTRAINT `FK_users_TO_order_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

