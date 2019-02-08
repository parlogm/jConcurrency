/* User */
insert into user (user_id, password, first_name, last_name, email, company, phone, address1, address2, country, postal, role, is_active, is_blocked, security_provider_id, default_customer_id, secret_question, secret_answer, enable_beta_testing, enable_renewal) values
('demo'      , '$2a$04$JfjPzFhWy7csaHz1WGpaeOkxd8Vq4owOg.FypBkjej19n8gGgm1mO'     , 'Demo'  , 'User', 'demo@user.org'    , 'Abshire Inc', '7-(740)701-4547', '80429 Garrison Crossing', '4967'               , 'USA'        , '64890', 'USER' , 1, 0, 10001, 20000, 'Diverse'       , 'Yellow' , 0, 0),
('admin'     , '$2a$04$BJMENJxtuTvLcGxWqbBvi.MD1Rj91joZEJWCz/48xuUSof5.JkIFm'    , 'Mihai'  , 'Parlog' , 'mihai.parlog@ing.com'     , 'Glover, Adams and Bins', '383-(779)851-3208', '30874 Graceland Terrace', '99152' , 'USA'        , '51065', 'ADMIN', 1, 0, 10001, 20000, 'knowledge base', 'Mauv'   , 1, 0),;

/* Servers */
insert into servers (id, name, address, environment, available_flag) values
(1, 'lrv154a6', 'lrv154a6.europe.intranet', 'Production', 0),
(2, 'lrv154cq', 'lrv154cq.europe.intranet', 'Production', 0),
(3, 'lrv160sk', 'lrv160sk.europe.intranet', 'Acceptance', 0),
(4, 'lrv160xi', 'lrv160xi.europe.intranet', 'Acceptance', 0),
(5, 'lrv153h4', 'lrv153h4.europe.intranet', 'Development', 0),
(6, 'lrv153fn', 'lrv153fn.europe.intranet', 'Development', 0);