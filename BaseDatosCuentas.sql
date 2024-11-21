CREATE TABLE public.t_accounts (
	act_id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	act_number varchar(255) NOT NULL,
	client_id int8 NOT NULL,
	act_initial_balance numeric(38, 2) NOT NULL,
	act_status bool NULL,
	act_type varchar(255) NOT NULL,
	CONSTRAINT t_accounts_act_initial_balance_check CHECK ((act_initial_balance >= (1)::numeric)),
	CONSTRAINT t_accounts_act_type_check CHECK (((act_type)::text = ANY ((ARRAY['SAVINGS'::character varying, 'CURRENT'::character varying])::text[]))),
	CONSTRAINT t_accounts_pkey PRIMARY KEY (act_id),
	CONSTRAINT uk987asfgn309nb5fjymok19ug7 UNIQUE (act_number)
);

CREATE TABLE public.t_movements (
	mov_id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	mov_balance numeric(38, 2) NOT NULL,
	mov_date timestamp(6) NOT NULL,
	mov_type varchar(255) NOT NULL,
	mov_value numeric(38, 2) NOT NULL,
	mov_act_id int8 NOT NULL,
	CONSTRAINT t_movements_mov_type_check CHECK (((mov_type)::text = ANY ((ARRAY['DEPOSIT'::character varying, 'WITHDRAWAL'::character varying])::text[]))),
	CONSTRAINT t_movements_pkey PRIMARY KEY (mov_id),
	CONSTRAINT fk8hg9qde2yb9vd6uv7eih2t3ic FOREIGN KEY (mov_act_id) REFERENCES public.t_accounts(act_id)
);