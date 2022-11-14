--
-- PostgreSQL database dump
--

-- Dumped from database version 12.12
-- Dumped by pg_dump version 12.12

-- Started on 2022-11-13 21:26:26

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 152179)
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- TOC entry 3816 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';


--
-- TOC entry 956 (class 1255 OID 153223)
-- Name: randompointsinpolygon(public.geometry, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.randompointsinpolygon(geom public.geometry, num_points integer) RETURNS SETOF public.geometry
    LANGUAGE plpgsql
    AS $$DECLARE
  target_proportion numeric;
  n_ret integer := 0;
  loops integer := 0;
  x_min float8;
  y_min float8;
  x_max float8;
  y_max float8;
  srid integer;
  rpoint geometry;
BEGIN
  -- Get envelope and SRID of source polygon
  SELECT ST_XMin(geom), ST_YMin(geom), ST_XMax(geom), ST_YMax(geom), ST_SRID(geom)
    INTO x_min, y_min, x_max, y_max, srid;
  -- Get the area proportion of envelope size to determine if a
  -- result can be returned in a reasonable amount of time
  SELECT ST_Area(geom)/ST_Area(ST_Envelope(geom)) INTO target_proportion;
  RAISE DEBUG 'geom: SRID %, NumGeometries %, NPoints %, area proportion within envelope %',
                srid, ST_NumGeometries(geom), ST_NPoints(geom),
                round(100.0*target_proportion, 2) || '%';
  IF target_proportion < 0.0001 THEN
    RAISE EXCEPTION 'Target area proportion of geometry is too low (%)', 
                    100.0*target_proportion || '%';
  END IF;
  RAISE DEBUG 'bounds: % % % %', x_min, y_min, x_max, y_max;
  
  WHILE n_ret < num_points LOOP
    loops := loops + 1;
    SELECT ST_SetSRID(ST_MakePoint(random()*(x_max - x_min) + x_min,
                                   random()*(y_max - y_min) + y_min),
                      srid) INTO rpoint;
    IF ST_Contains(geom, rpoint) THEN
      n_ret := n_ret + 1;
      RETURN NEXT rpoint;
    END IF;
  END LOOP;
  RAISE DEBUG 'determined in % loops (% efficiency)', loops, round(100.0*num_points/loops, 2) || '%';
END$$;


ALTER FUNCTION public.randompointsinpolygon(geom public.geometry, num_points integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 153349)
-- Name: chile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chile (
    st_union public.geometry
);


ALTER TABLE public.chile OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 153224)
-- Name: division_regional; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.division_regional (
    gid integer NOT NULL,
    nom_reg character varying(50),
    nom_prov character varying(20),
    cod_com character varying(5),
    nom_com character varying(30),
    cod_regi numeric,
    superficie numeric,
    poblac02 integer,
    pobl2010 integer,
    shape_leng numeric,
    shape_area numeric,
    geom public.geometry(MultiPolygon)
);


ALTER TABLE public.division_regional OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 153230)
-- Name: division_regional_gid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.division_regional_gid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.division_regional_gid_seq OWNER TO postgres;

--
-- TOC entry 3817 (class 0 OID 0)
-- Dependencies: 209
-- Name: division_regional_gid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.division_regional_gid_seq OWNED BY public.division_regional.gid;


--
-- TOC entry 210 (class 1259 OID 153232)
-- Name: eme_habilidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eme_habilidad (
    id numeric(8,0) NOT NULL,
    id_emergencia numeric(6,0),
    id_habilidad numeric(4,0)
);


ALTER TABLE public.eme_habilidad OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 153235)
-- Name: emergencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emergencia (
    id numeric(6,0) NOT NULL,
    nombre character varying(100),
    descrip character varying(400),
    finicio date,
    ffin date,
    id_institucion numeric(4,0),
    location public.geometry(Point)
);


ALTER TABLE public.emergencia OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 153241)
-- Name: estado_tarea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estado_tarea (
    id numeric(2,0) NOT NULL,
    descrip character varying(20)
);


ALTER TABLE public.estado_tarea OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 153244)
-- Name: habilidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.habilidad (
    id numeric(4,0) NOT NULL,
    descrip character varying(100)
);


ALTER TABLE public.habilidad OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 153247)
-- Name: institucion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.institucion (
    id numeric(4,0) NOT NULL,
    nombre character varying(100),
    descrip character varying(400)
);


ALTER TABLE public.institucion OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 153253)
-- Name: ranking; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ranking (
    id numeric(8,0) NOT NULL,
    id_voluntario numeric(8,0),
    id_tarea numeric(8,0),
    puntaje numeric(3,0),
    flg_invitado numeric(1,0),
    flg_participa numeric(1,0)
);


ALTER TABLE public.ranking OWNER TO postgres;

--
-- TOC entry 3818 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE ranking; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.ranking IS 'los flgInvitado, flgParticipa 1 si la respuesta es si, 2 si la respuesta es no';


--
-- TOC entry 216 (class 1259 OID 153256)
-- Name: tarea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tarea (
    id numeric(8,0) NOT NULL,
    nombre character varying(60),
    descrip character varying(300),
    cant_vol_requeridos numeric(4,0),
    cant_vol_inscritos numeric(4,0),
    id_emergencia numeric(6,0),
    finicio date,
    ffin date,
    id_estado numeric(2,0)
);


ALTER TABLE public.tarea OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 153259)
-- Name: tarea_habilidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tarea_habilidad (
    id numeric(8,0) NOT NULL,
    id_emehab numeric(8,0),
    id_tarea numeric(8,0)
);


ALTER TABLE public.tarea_habilidad OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 153262)
-- Name: vol_habilidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vol_habilidad (
    id numeric(8,0) NOT NULL,
    id_voluntario numeric(8,0),
    id_habilidad numeric(8,0)
);


ALTER TABLE public.vol_habilidad OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 153265)
-- Name: voluntario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voluntario (
    id numeric(8,0) NOT NULL,
    nombre character varying(100),
    fnacimiento date,
    location public.geometry(Point)
);


ALTER TABLE public.voluntario OWNER TO postgres;

--
-- TOC entry 3631 (class 2604 OID 153271)
-- Name: division_regional gid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.division_regional ALTER COLUMN gid SET DEFAULT nextval('public.division_regional_gid_seq'::regclass);


--
-- TOC entry 3810 (class 0 OID 153349)
-- Dependencies: 220
-- Data for Name: chile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chile (st_union) FROM stdin;
\N
\.


--
-- TOC entry 3798 (class 0 OID 153224)
-- Dependencies: 208
-- Data for Name: division_regional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.division_regional (gid, nom_reg, nom_prov, cod_com, nom_com, cod_regi, superficie, poblac02, pobl2010, shape_leng, shape_area, geom) FROM stdin;
\.


--
-- TOC entry 3800 (class 0 OID 153232)
-- Dependencies: 210
-- Data for Name: eme_habilidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.eme_habilidad (id, id_emergencia, id_habilidad) FROM stdin;
\.


--
-- TOC entry 3801 (class 0 OID 153235)
-- Dependencies: 211
-- Data for Name: emergencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emergencia (id, nombre, descrip, finicio, ffin, id_institucion, location) FROM stdin;
\.


--
-- TOC entry 3802 (class 0 OID 153241)
-- Dependencies: 212
-- Data for Name: estado_tarea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estado_tarea (id, descrip) FROM stdin;
\.


--
-- TOC entry 3803 (class 0 OID 153244)
-- Dependencies: 213
-- Data for Name: habilidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.habilidad (id, descrip) FROM stdin;
\.


--
-- TOC entry 3804 (class 0 OID 153247)
-- Dependencies: 214
-- Data for Name: institucion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.institucion (id, nombre, descrip) FROM stdin;
\.


--
-- TOC entry 3805 (class 0 OID 153253)
-- Dependencies: 215
-- Data for Name: ranking; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ranking (id, id_voluntario, id_tarea, puntaje, flg_invitado, flg_participa) FROM stdin;
\.


--
-- TOC entry 3629 (class 0 OID 152491)
-- Dependencies: 204
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
\.


--
-- TOC entry 3806 (class 0 OID 153256)
-- Dependencies: 216
-- Data for Name: tarea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tarea (id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado) FROM stdin;
\.


--
-- TOC entry 3807 (class 0 OID 153259)
-- Dependencies: 217
-- Data for Name: tarea_habilidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tarea_habilidad (id, id_emehab, id_tarea) FROM stdin;
\.


--
-- TOC entry 3808 (class 0 OID 153262)
-- Dependencies: 218
-- Data for Name: vol_habilidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vol_habilidad (id, id_voluntario, id_habilidad) FROM stdin;
\.


--
-- TOC entry 3809 (class 0 OID 153265)
-- Dependencies: 219
-- Data for Name: voluntario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.voluntario (id, nombre, fnacimiento, location) FROM stdin;
\.


--
-- TOC entry 3819 (class 0 OID 0)
-- Dependencies: 209
-- Name: division_regional_gid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.division_regional_gid_seq', 23, true);


--
-- TOC entry 3635 (class 2606 OID 153273)
-- Name: division_regional division_regional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.division_regional
    ADD CONSTRAINT division_regional_pkey PRIMARY KEY (gid);


--
-- TOC entry 3637 (class 2606 OID 153275)
-- Name: eme_habilidad eme_habilidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT eme_habilidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3639 (class 2606 OID 153277)
-- Name: emergencia emergencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT emergencia_pkey PRIMARY KEY (id);


--
-- TOC entry 3641 (class 2606 OID 153279)
-- Name: estado_tarea estado_tarea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estado_tarea
    ADD CONSTRAINT estado_tarea_pkey PRIMARY KEY (id);


--
-- TOC entry 3643 (class 2606 OID 153281)
-- Name: habilidad habilidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.habilidad
    ADD CONSTRAINT habilidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3645 (class 2606 OID 153283)
-- Name: institucion institucion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id);


--
-- TOC entry 3647 (class 2606 OID 153285)
-- Name: ranking ranking_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT ranking_pkey PRIMARY KEY (id);


--
-- TOC entry 3651 (class 2606 OID 153287)
-- Name: tarea_habilidad tarea_habilidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT tarea_habilidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3649 (class 2606 OID 153289)
-- Name: tarea tarea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (id);


--
-- TOC entry 3653 (class 2606 OID 153291)
-- Name: vol_habilidad vol_habilidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT vol_habilidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3655 (class 2606 OID 153293)
-- Name: voluntario voluntario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voluntario
    ADD CONSTRAINT voluntario_pkey PRIMARY KEY (id);


--
-- TOC entry 3656 (class 2606 OID 153294)
-- Name: eme_habilidad fk_emehab_emergencia; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id);


--
-- TOC entry 3657 (class 2606 OID 153299)
-- Name: eme_habilidad fk_emehab_habilidad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eme_habilidad
    ADD CONSTRAINT fk_emehab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id);


--
-- TOC entry 3658 (class 2606 OID 153304)
-- Name: emergencia fk_emergencia_institucion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergencia
    ADD CONSTRAINT fk_emergencia_institucion FOREIGN KEY (id_institucion) REFERENCES public.institucion(id);


--
-- TOC entry 3659 (class 2606 OID 153309)
-- Name: ranking fk_ranking_tarea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id);


--
-- TOC entry 3660 (class 2606 OID 153314)
-- Name: ranking fk_ranking_voluntario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ranking
    ADD CONSTRAINT fk_ranking_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id);


--
-- TOC entry 3661 (class 2606 OID 153319)
-- Name: tarea fk_tarea_emergencia; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT fk_tarea_emergencia FOREIGN KEY (id_emergencia) REFERENCES public.emergencia(id);


--
-- TOC entry 3662 (class 2606 OID 153324)
-- Name: tarea fk_tarea_estadotarea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT fk_tarea_estadotarea FOREIGN KEY (id_estado) REFERENCES public.estado_tarea(id);


--
-- TOC entry 3663 (class 2606 OID 153329)
-- Name: tarea_habilidad fk_tareahab_emehab; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_emehab FOREIGN KEY (id_emehab) REFERENCES public.eme_habilidad(id);


--
-- TOC entry 3664 (class 2606 OID 153334)
-- Name: tarea_habilidad fk_tareahab_tarea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea_habilidad
    ADD CONSTRAINT fk_tareahab_tarea FOREIGN KEY (id_tarea) REFERENCES public.tarea(id);


--
-- TOC entry 3665 (class 2606 OID 153339)
-- Name: vol_habilidad fk_volhab_habilidad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_habilidad FOREIGN KEY (id_habilidad) REFERENCES public.habilidad(id);


--
-- TOC entry 3666 (class 2606 OID 153344)
-- Name: vol_habilidad fk_volhab_voluntario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol_habilidad
    ADD CONSTRAINT fk_volhab_voluntario FOREIGN KEY (id_voluntario) REFERENCES public.voluntario(id);


-- Completed on 2022-11-13 21:26:26

--
-- PostgreSQL database dump complete
--

