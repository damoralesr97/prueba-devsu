CREATE TABLE public.t_persons (
	per_id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	per_address varchar(255) NOT NULL,
	per_age int4 NOT NULL,
	per_dni varchar(10) NOT NULL,
	per_genre varchar(255) NOT NULL,
	per_name varchar(255) NOT NULL,
	per_phone varchar(255) NOT NULL,
	CONSTRAINT t_persons_per_age_check CHECK ((per_age >= 18)),
	CONSTRAINT t_persons_per_genre_check CHECK (((per_genre)::text = ANY ((ARRAY['M'::character varying, 'F'::character varying])::text[]))),
	CONSTRAINT t_persons_pkey PRIMARY KEY (per_id),
	CONSTRAINT ukfh2ugalpie9vwaghw34br4f7l UNIQUE (per_dni)
);

CREATE TABLE public.t_clients (
	cli_password varchar(255) NOT NULL,
	cli_status bool NOT NULL,
	per_id int8 NOT NULL,
	CONSTRAINT t_clients_pkey PRIMARY KEY (per_id),
	CONSTRAINT fkf2gqdav15y23k29hnrfxbu7al FOREIGN KEY (per_id) REFERENCES public.t_persons(per_id)
);