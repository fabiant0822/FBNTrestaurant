-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2018. Ápr 09. 02:36
-- Kiszolgáló verziója: 5.7.19
-- PHP verzió: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `etterem`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `asztalok`
--

DROP TABLE IF EXISTS `asztalok`;
CREATE TABLE IF NOT EXISTS `asztalok` (
  `asztal` int(11) NOT NULL,
  `szek` int(11) NOT NULL,
  `helyseg` varchar(20) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`asztal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `asztalok`
--

INSERT INTO `asztalok` (`asztal`, `szek`, `helyseg`) VALUES
(1, 4, 'belső terem'),
(2, 4, 'belső terem'),
(3, 6, 'belső terem'),
(4, 2, 'belső terem'),
(5, 4, 'belső terem'),
(6, 8, 'belső terem'),
(7, 2, 'belső terem'),
(8, 2, 'külső terem'),
(9, 6, 'külső terem'),
(10, 4, 'külső terem'),
(11, 2, 'külső terem'),
(12, 4, 'külső terem'),
(13, 8, 'külső terem'),
(14, 8, 'kerthelység'),
(15, 6, 'kerthelység'),
(16, 8, 'kerthelység'),
(17, 8, 'kerthelység'),
(18, 6, 'kerthelység'),
(19, 6, 'kerthelység'),
(20, 6, 'kerthelység');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `password` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('admin', 'admin');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rendelesek`
--

DROP TABLE IF EXISTS `rendelesek`;
CREATE TABLE IF NOT EXISTS `rendelesek` (
  `rendelesID` int(11) NOT NULL AUTO_INCREMENT,
  `asztal` int(2) NOT NULL,
  `tetelID` int(11) NOT NULL,
  `mennyiseg` int(11) NOT NULL,
  PRIMARY KEY (`rendelesID`),
  KEY `asztal` (`asztal`),
  KEY `tetel ID` (`tetelID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `rendelesek`
--

INSERT INTO `rendelesek` (`rendelesID`, `asztal`, `tetelID`, `mennyiseg`) VALUES
(17, 1, 52, 3),
(18, 1, 1, 2),
(19, 1, 14, 3),
(20, 1, 29, 1),
(21, 1, 36, 1),
(22, 1, 32, 1),
(23, 1, 43, 3);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `tetelek`
--

DROP TABLE IF EXISTS `tetelek`;
CREATE TABLE IF NOT EXISTS `tetelek` (
  `tetelID` int(5) NOT NULL AUTO_INCREMENT,
  `tetel` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `egysegar` int(11) NOT NULL,
  `egyseg` varchar(5) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`tetelID`),
  KEY `tetel` (`tetel`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `tetelek`
--

INSERT INTO `tetelek` (`tetelID`, `tetel`, `egysegar`, `egyseg`) VALUES
(1, 'Marha tatár, fűszervajjal és friss zöldségekkel', 2600, 'adag'),
(2, 'Őz carpaccio parmezánforgáccsal, szezámmag olajjal', 2500, 'adag'),
(3, 'Füstölt, citromos lazactekercs snidlingpürével', 2200, 'adag'),
(4, 'Libamáj terrine jégborban érlelt fügeraguval', 2500, 'adag'),
(5, 'Pirított kecskesajt kéksajtos rukkolaágyon', 2100, 'adag'),
(6, 'Narancsos kacsamell gránátalmás zöldsalátával', 2100, 'adag'),
(10, 'Halászlé ponty', 1200, 'adag'),
(11, 'Halászlé harcsa', 1400, 'pohár'),
(12, 'Tárkonyos vadragu leves erdei gombákkal', 850, 'adag'),
(14, 'Kapros-savanyú káposztás borjúragu leves', 850, 'adag'),
(18, 'Tyúkhúsleves lúdgége tésztával', 900, 'adag'),
(19, 'Fokhagyma krémleves', 600, 'adag'),
(20, 'Vargánya krémleves házi gnocchi-val', 900, 'adag'),
(21, 'Grillezett jércemell filé füstölt sajt gratinnal', 2000, 'adag'),
(22, 'Ropogós bundában sült jércemell  édes chilivel', 1900, 'adag'),
(23, 'Grillezett libamájszeletek sült almával', 2200, 'adag'),
(24, 'Óriás bécsi szelet sertésszűzből', 2200, 'adag'),
(25, 'Érlelt marhahátszín roston', 2800, 'adag'),
(26, 'Érlelt bélszín tournedo (250 gr)', 4500, 'adag'),
(27, 'Bélszíncsíkok erdei gombával és sonkával', 2900, 'adag'),
(28, 'Schweinebraten pecsenyeszafttal', 1900, 'adag'),
(29, 'Ropogós csülök pergelt bajorkáposztával ', 2200, 'adag'),
(30, 'Sörben sült konfitált malaccsászár ', 2500, 'adag'),
(31, 'Harcsasteak roquefortos friss salátával', 2300, 'adag'),
(32, 'Harcsapaprikás házi túrós csuszával', 2300, 'adag'),
(33, 'Fokhagymás fogasfilé füstöltsajt gratinnal', 2800, 'adag'),
(34, 'Grillezett lazacfilé tejszínes mustármártásban', 2900, 'adag'),
(35, 'Lazacsteak vajas-fokhagymás bébispenóttal', 2800, 'adag'),
(36, 'Szarvas java vadas mártásban', 3000, 'adag'),
(37, 'Érlelt őzgerinc erdei gyümölcsraguval', 3200, 'adag'),
(38, 'Erdei gombás szarvas ragu portói mártásban', 3200, 'adag'),
(39, 'Vörösboros vaddisznópörkölt', 2800, 'adag'),
(40, 'Mustáros vaddisznóragu gyöngyhagymával', 3100, 'adag'),
(41, 'Meggyes-csokoládés palacsinta', 750, 'adag'),
(42, 'Bajor almás-fahéjas palacsinta ', 550, 'adag'),
(43, 'Szigeti mákos gőzgombóc, vanília sodóval', 1300, 'adag'),
(44, 'Brownie vanília fagylalttal', 700, 'adag'),
(45, 'Paulaner 0,5l', 850, 'korsó'),
(46, 'Paulaner 0,3l', 650, 'pohár'),
(47, 'Dreher 0,5l', 750, 'korsó'),
(48, 'Dreher 0,3l', 450, 'pohár'),
(49, 'Coca cola 0,2 dl', 450, 'üveg'),
(50, 'Fanta 0,2 dl', 450, 'üveg'),
(51, 'Sprite 0,2 dl', 450, 'üveg'),
(52, 'Almdudler 0,3 dl', 650, 'üveg');

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `rendelesek`
--
ALTER TABLE `rendelesek`
  ADD CONSTRAINT `etterem1` FOREIGN KEY (`asztal`) REFERENCES `asztalok` (`asztal`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `etterem2` FOREIGN KEY (`tetelID`) REFERENCES `tetelek` (`tetelID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
