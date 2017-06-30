package br.com.trixti.itm.util;

public class UtilEmail {
	public static String corpo = "	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" + 
			"	<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
			"	<head>\n" + 
			"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" + 
			"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" + 
			"		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\n" + 
			"		<meta name=\"format-detection\" content=\"telephone=no\" /> <!-- disable auto telephone linking in iOS -->\n" + 
			"		<title>Respmail is a response HTML email designed to work on all major email platforms and smartphones</title>\n" + 
			"		<style type=\"text/css\">\n" + 
			"			/* RESET STYLES */\n" + 
			"			html { background-color:#E1E1E1; margin:0; padding:0; }\n" + 
			"			body, #bodyTable, #bodyCell, #bodyCell{height:100% !important; margin:0; padding:0; width:100% !important;font-family:Helvetica, Arial, \"Lucida Grande\", sans-serif;}\n" + 
			"			table{border-collapse:collapse;}\n" + 
			"			table[id=bodyTable] {width:100%!important;margin:auto;max-width:500px!important;color:#7A7A7A;font-weight:normal;}\n" + 
			"			img, a img{border:0; outline:none; text-decoration:none;height:auto; line-height:100%;}\n" + 
			"			a {text-decoration:none !important;border-bottom: 1px solid;}\n" + 
			"			h1, h2, h3, h4, h5, h6{color:#5F5F5F; font-weight:normal; font-family:Helvetica; font-size:20px; line-height:125%; text-align:Left; letter-spacing:normal;margin-top:0;margin-right:0;margin-bottom:10px;margin-left:0;padding-top:0;padding-bottom:0;padding-left:0;padding-right:0;}\n" + 
			"\n" + 
			"			/* CLIENT-SPECIFIC STYLES */\n" + 
			"			.ReadMsgBody{width:100%;} .ExternalClass{width:100%;} /* Force Hotmail/Outlook.com to display emails at full width. */\n" + 
			"			.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height:100%;} /* Force Hotmail/Outlook.com to display line heights normally. */\n" + 
			"			table, td{mso-table-lspace:0pt; mso-table-rspace:0pt;} /* Remove spacing between tables in Outlook 2007 and up. */\n" + 
			"			#outlook a{padding:0;} /* Force Outlook 2007 and up to provide a \"view in browser\" message. */\n" + 
			"			img{-ms-interpolation-mode: bicubic;display:block;outline:none; text-decoration:none;} /* Force IE to smoothly render resized images. */\n" + 
			"			body, table, td, p, a, li, blockquote{-ms-text-size-adjust:100%; -webkit-text-size-adjust:100%; font-weight:normal!important;} /* Prevent Windows- and Webkit-based mobile platforms from changing declared text sizes. */\n" + 
			"			.ExternalClass td[class=\"ecxflexibleContainerBox\"] h3 {padding-top: 10px !important;} /* Force hotmail to push 2-grid sub headers down */\n" + 
			"\n" + 
			"			/* /\\/\\/\\/\\/\\/\\/\\/\\/ TEMPLATE STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" + 
			"\n" + 
			"			/* ========== Page Styles ========== */\n" + 
			"			h1{display:block;font-size:26px;font-style:normal;font-weight:normal;line-height:100%;}\n" + 
			"			h2{display:block;font-size:20px;font-style:normal;font-weight:normal;line-height:120%;}\n" + 
			"			h3{display:block;font-size:17px;font-style:normal;font-weight:normal;line-height:110%;}\n" + 
			"			h4{display:block;font-size:18px;font-style:italic;font-weight:normal;line-height:100%;}\n" + 
			"			.flexibleImage{height:auto;}\n" + 
			"			.linkRemoveBorder{border-bottom:0 !important;}\n" + 
			"			table[class=flexibleContainerCellDivider] {padding-bottom:0 !important;padding-top:0 !important;}\n" + 
			"\n" + 
			"			body, #bodyTable{background-color:#E1E1E1;}\n" + 
			"			#emailHeader{background-color:#E1E1E1;}\n" + 
			"			#emailBody{background-color:#FFFFFF;}\n" + 
			"			#emailFooter{background-color:#E1E1E1;}\n" + 
			"			.nestedContainer{background-color:#F8F8F8; border:1px solid #CCCCCC;}\n" + 
			"			.emailButton{background-color:#205478; border-collapse:separate;}\n" + 
			"			.buttonContent{color:#FFFFFF; font-family:Helvetica; font-size:18px; font-weight:bold; line-height:100%; padding:15px; text-align:center;}\n" + 
			"			.buttonContent a{color:#FFFFFF; display:block; text-decoration:none!important; border:0!important;}\n" + 
			"			.emailCalendar{background-color:#FFFFFF; border:1px solid #CCCCCC;}\n" + 
			"			.emailCalendarMonth{background-color:#205478; color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; padding-top:10px; padding-bottom:10px; text-align:center;}\n" + 
			"			.emailCalendarDay{color:#205478; font-family:Helvetica, Arial, sans-serif; font-size:60px; font-weight:bold; line-height:100%; padding-top:20px; padding-bottom:20px; text-align:center;}\n" + 
			"			.imageContentText {margin-top: 10px;line-height:0;}\n" + 
			"			.imageContentText a {line-height:0;}\n" + 
			"			#invisibleIntroduction {display:none !important;} /* Removing the introduction text from the view */\n" + 
			"\n" + 
			"			span[class=ios-color-hack] a {color:#275100!important;text-decoration:none!important;} /* Remove all link colors in IOS (below are duplicates based on the color preference) */\n" + 
			"			span[class=ios-color-hack2] a {color:#205478!important;text-decoration:none!important;}\n" + 
			"			span[class=ios-color-hack3] a {color:#8B8B8B!important;text-decoration:none!important;}\n" + 
			"			/* A nice and clean way to target phone numbers you want clickable and avoid a mobile phone from linking other numbers that look like, but are not phone numbers.  Use these two blocks of code to \"unstyle\" any numbers that may be linked.  The second block gives you a class to apply with a span tag to the numbers you would like linked and styled.\n" + 
			"			Inspired by Campaign Monitor's article on using phone numbers in email: http://www.campaignmonitor.com/blog/post/3571/using-phone-numbers-in-html-email/.\n" + 
			"			*/\n" + 
			"			.a[href^=\"tel\"], a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:none!important;cursor:default!important;}\n" + 
			"			.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:auto!important;cursor:default!important;}\n" + 
			"\n" + 
			"\n" + 
			"			/* MOBILE STYLES */\n" + 
			"			@media only screen and (max-width: 480px){\n" + 
			"				/*////// CLIENT-SPECIFIC STYLES //////*/\n" + 
			"				body{width:100% !important; min-width:100% !important;} /* Force iOS Mail to render the email at full width. */\n" + 
			"\n" + 
			"				/* FRAMEWORK STYLES */\n" + 
			"				/*\n" + 
			"				CSS selectors are written in attribute\n" + 
			"				selector format to prevent Yahoo Mail\n" + 
			"				from rendering media query styles on\n" + 
			"				desktop.\n" + 
			"				*/\n" + 
			"				/*td[class=\"textContent\"], td[class=\"flexibleContainerCell\"] { width: 100%; padding-left: 10px !important; padding-right: 10px !important; }*/\n" + 
			"				table[id=\"emailHeader\"],\n" + 
			"				table[id=\"emailBody\"],\n" + 
			"				table[id=\"emailFooter\"],\n" + 
			"				table[class=\"flexibleContainer\"],\n" + 
			"				td[class=\"flexibleContainerCell\"] {width:100% !important;}\n" + 
			"				td[class=\"flexibleContainerBox\"], td[class=\"flexibleContainerBox\"] table {display: block;width: 100%;text-align: left;}\n" + 
			"				/*\n" + 
			"				The following style rule makes any\n" + 
			"				image classed with 'flexibleImage'\n" + 
			"				fluid when the query activates.\n" + 
			"				Make sure you add an inline max-width\n" + 
			"				to those images to prevent them\n" + 
			"				from blowing out.\n" + 
			"				*/\n" + 
			"				td[class=\"imageContent\"] img {height:auto !important; width:100% !important; max-width:100% !important; }\n" + 
			"				img[class=\"flexibleImage\"]{height:auto !important; width:100% !important;max-width:100% !important;}\n" + 
			"				img[class=\"flexibleImageSmall\"]{height:auto !important; width:auto !important;}\n" + 
			"\n" + 
			"\n" + 
			"				/*\n" + 
			"				Create top space for every second element in a block\n" + 
			"				*/\n" + 
			"				table[class=\"flexibleContainerBoxNext\"]{padding-top: 10px !important;}\n" + 
			"\n" + 
			"				/*\n" + 
			"				Make buttons in the email span the\n" + 
			"				full width of their container, allowing\n" + 
			"				for left- or right-handed ease of use.\n" + 
			"				*/\n" + 
			"				table[class=\"emailButton\"]{width:100% !important;}\n" + 
			"				td[class=\"buttonContent\"]{padding:0 !important;}\n" + 
			"				td[class=\"buttonContent\"] a{padding:15px !important;}\n" + 
			"\n" + 
			"			}\n" + 
			"\n" + 
			"			/*  CONDITIONS FOR ANDROID DEVICES ONLY\n" + 
			"			*   http://developer.android.com/guide/webapps/targeting.html\n" + 
			"			*   http://pugetworks.com/2011/04/css-media-queries-for-targeting-different-mobile-devices/ ;\n" + 
			"			=====================================================*/\n" + 
			"\n" + 
			"			@media only screen and (-webkit-device-pixel-ratio:.75){\n" + 
			"				/* Put CSS for low density (ldpi) Android layouts in here */\n" + 
			"			}\n" + 
			"\n" + 
			"			@media only screen and (-webkit-device-pixel-ratio:1){\n" + 
			"				/* Put CSS for medium density (mdpi) Android layouts in here */\n" + 
			"			}\n" + 
			"\n" + 
			"			@media only screen and (-webkit-device-pixel-ratio:1.5){\n" + 
			"				/* Put CSS for high density (hdpi) Android layouts in here */\n" + 
			"			}\n" + 
			"			/* end Android targeting */\n" + 
			"\n" + 
			"			/* CONDITIONS FOR IOS DEVICES ONLY\n" + 
			"			=====================================================*/\n" + 
			"			@media only screen and (min-device-width : 320px) and (max-device-width:568px) {\n" + 
			"\n" + 
			"			}\n" + 
			"			/* end IOS targeting */\n" + 
			"		</style>\n" + 
			"		<!--\n" + 
			"			Outlook Conditional CSS\n" + 
			"\n" + 
			"			These two style blocks target Outlook 2007 & 2010 specifically, forcing\n" + 
			"			columns into a single vertical stack as on mobile clients. This is\n" + 
			"			primarily done to avoid the 'page break bug' and is optional.\n" + 
			"\n" + 
			"			More information here:\n" + 
			"			http://templates.mailchimp.com/development/css/outlook-conditional-css\n" + 
			"		-->\n" + 
			"		<!--[if mso 12]>\n" + 
			"			<style type=\"text/css\">\n" + 
			"				.flexibleContainer{display:block !important; width:100% !important;}\n" + 
			"			</style>\n" + 
			"		<![endif]-->\n" + 
			"		<!--[if mso 14]>\n" + 
			"			<style type=\"text/css\">\n" + 
			"				.flexibleContainer{display:block !important; width:100% !important;}\n" + 
			"			</style>\n" + 
			"		<![endif]-->\n" + 
			"	</head>\n" + 
			"	<body bgcolor=\"#E1E1E1\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\">\n" + 
			"\n" + 
			"		<!-- CENTER THE EMAIL // -->\n" + 
			"		<!--\n" + 
			"		1.  The center tag should normally put all the\n" + 
			"			content in the middle of the email page.\n" + 
			"			I added \"table-layout: fixed;\" style to force\n" + 
			"			yahoomail which by default put the content left.\n" + 
			"\n" + 
			"		2.  For hotmail and yahoomail, the contents of\n" + 
			"			the email starts from this center, so we try to\n" + 
			"			apply necessary styling e.g. background-color.\n" + 
			"		-->\n" + 
			"		<center style=\"background-color:#E1E1E1;\">\n" + 
			"			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\" style=\"table-layout: fixed;max-width:100% !important;width: 100% !important;min-width: 100% !important;\">\n" + 
			"				<tr>\n" + 
			"					<td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" + 
			"\n" + 
			"						<table bgcolor=\"#E1E1E1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailHeader\">\n" + 
			"\n" + 
			"							<!-- HEADER ROW // -->\n" + 
			"							<tr>\n" + 
			"								<td align=\"center\" valign=\"top\">\n" + 
			"									<!-- CENTERING TABLE // -->\n" + 
			"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" + 
			"										<tr>\n" + 
			"											<td align=\"center\" valign=\"top\">\n" + 
			"												<!-- FLEXIBLE CONTAINER // -->\n" + 
			"												<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" + 
			"													<tr>\n" + 
			"														<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" + 
			"\n" + 
			"															<!-- CONTENT TABLE // -->\n" + 
			"															\n" + 
			"														</td>\n" + 
			"													</tr>\n" + 
			"												</table>\n" + 
			"												<!-- // FLEXIBLE CONTAINER -->\n" + 
			"											</td>\n" + 
			"										</tr>\n" + 
			"									</table>\n" + 
			"									<!-- // CENTERING TABLE -->\n" + 
			"								</td>\n" + 
			"							</tr>\n" + 
			"							<!-- // END -->\n" + 
			"\n" + 
			"						</table>\n" + 
			"						<!-- // END -->\n" + 
			"\n" + 
			"						<!-- EMAIL BODY // -->\n" + 
			"						<!--\n" + 
			"							The table \"emailBody\" is the email's container.\n" + 
			"							Its width can be set to 100% for a color band\n" + 
			"							that spans the width of the page.\n" + 
			"						-->\n" + 
			"						<table bgcolor=\"#FFFFFF\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailBody\">\n" + 
			"\n" + 
			"							<!-- MODULE ROW // -->\n" + 
			"							<!--\n" + 
			"								To move or duplicate any of the design patterns\n" + 
			"								in this email, simply move or copy the entire\n" + 
			"								MODULE ROW section for each content block.\n" + 
			"							-->\n" + 
			"							<tr>\n" + 
			"								<td align=\"center\" valign=\"top\">\n" + 
			"									<!-- CENTERING TABLE // -->\n" + 
			"									<!--\n" + 
			"										The centering table keeps the content\n" + 
			"										tables centered in the emailBody table,\n" + 
			"										in case its width is set to 100%.\n" + 
			"									-->\n" + 
			"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"color:#FFFFFF;\" bgcolor=\"#3498db\">\n" + 
			"										<tr>\n" + 
			"											<td align=\"center\" valign=\"top\">\n" + 
			"												<!-- FLEXIBLE CONTAINER // -->\n" + 
			"												<!--\n" + 
			"													The flexible container has a set width\n" + 
			"													that gets overridden by the media query.\n" + 
			"													Most content tables within can then be\n" + 
			"													given 100% widths.\n" + 
			"												-->\n" + 
			"												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" + 
			"													<tr>\n" + 
			"														<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" + 
			"\n" + 
			"															<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" + 
			"																<tr>\n" + 
			"																	<td align=\"center\" valign=\"top\" class=\"textContent\">\n" + 
			"																		<div class=\"container\">\n" + 
			"																			<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"																				<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" + 
			"																				<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" + 
			"																				<svg version=\"1.1\" id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\"\n" + 
			"																					 width=\"250px\" height=\"59.377px\" viewBox=\"0 0 250 59.377\" enable-background=\"new 0 0 250 59.377\" xml:space=\"preserve\">\n" + 
			"																				<g>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M250,32.744c-0.048,0.023-0.121,0.034-0.139,0.071c-0.564,1.18-1.625,1.789-2.722,2.34\n" + 
			"																						c-2.615,1.314-5.416,1.631-8.295,1.525c-2.025-0.075-3.868-0.757-5.681-1.603c-2.736-1.277-5.498-2.498-8.247-3.746\n" + 
			"																						c-2.421-1.099-4.844-2.195-7.257-3.312c-0.415-0.192-0.745-0.193-1.164,0.018c-3.204,1.615-6.422,3.2-9.634,4.798\n" + 
			"																						c-1.86,0.925-3.713,1.866-5.578,2.783c-1.481,0.729-3.066,1.032-4.707,1.079c-1.729,0.05-3.443-0.086-5.126-0.478\n" + 
			"																						c-1.593-0.371-3.093-0.992-4.415-1.966c-1.835-1.353-1.879-3.223-0.054-4.592c0.81-0.608,1.759-1.045,2.679-1.488\n" + 
			"																						c1.622-0.781,3.27-1.506,4.908-2.254c1.814-0.829,3.628-1.655,5.441-2.486c1.196-0.548,2.39-1.101,3.584-1.654\n" + 
			"																						c0.07-0.032,0.131-0.086,0.273-0.181c-0.378-0.185-0.69-0.346-1.01-0.493c-2.591-1.193-5.184-2.385-7.776-3.575\n" + 
			"																						c-2.354-1.08-4.723-2.13-7.056-3.255c-0.621-0.3-1.201-0.761-1.681-1.262c-0.973-1.016-0.913-2.416,0.054-3.449\n" + 
			"																						c0.837-0.894,1.908-1.412,3.017-1.866c2.058-0.843,4.217-1.156,6.424-1.187c2.326-0.032,4.467,0.609,6.528,1.684\n" + 
			"																						c3.268,1.705,6.584,3.317,9.883,4.963c1.499,0.748,3.002,1.49,4.515,2.21c0.179,0.085,0.464,0.085,0.647,0.004\n" + 
			"																						c2-0.887,3.99-1.798,5.983-2.702c2.409-1.092,4.818-2.187,7.23-3.275c1.489-0.672,2.978-1.345,4.476-1.997\n" + 
			"																						c1.169-0.508,2.407-0.724,3.673-0.831c1.478-0.125,2.94-0.019,4.39,0.25c1.855,0.344,3.629,0.928,5.21,1.984\n" + 
			"																						c0.602,0.402,1.128,0.901,1.433,1.587c0.034,0.077,0.128,0.127,0.194,0.189c0,0.528,0,1.056,0,1.583\n" + 
			"																						c-0.052,0.044-0.13,0.077-0.152,0.132c-0.41,1.005-1.207,1.619-2.153,2.034c-1.698,0.745-3.415,1.448-5.129,2.157\n" + 
			"																						c-3.747,1.552-7.497,3.096-11.245,4.645c-0.359,0.149-0.714,0.31-1.175,0.51c0.242,0.127,0.368,0.206,0.503,0.262\n" + 
			"																						c2.43,1.005,4.861,2.008,7.292,3.011c3.122,1.288,6.25,2.56,9.363,3.87c1.087,0.457,2.076,1.071,2.542,2.253\n" + 
			"																						c0.021,0.054,0.102,0.084,0.154,0.125C250,31.689,250,32.216,250,32.744z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M0,9.161c0.065-0.098,0.128-0.197,0.195-0.293c0.06-0.085,0.126-0.167,0.189-0.25\n" + 
			"																						c0.077,0.086,0.172,0.161,0.228,0.259c0.443,0.781,0.781,1.642,1.332,2.336c2.488,3.138,5.768,4.328,9.649,3.427\n" + 
			"																						c3.195-0.741,5.397-2.781,6.586-5.868c0.057-0.149,0.126-0.294,0.231-0.538c0.347,0.555,0.571,1.038,0.571,1.603\n" + 
			"																						c0.001,3.86,0.001,7.721,0.001,11.581c0,3.388-0.01,6.777,0.006,10.165c0.006,1.204-0.526,2.083-1.479,2.759\n" + 
			"																						c-1.05,0.746-2.257,1.12-3.479,1.435c-1.646,0.424-3.326,0.629-5.027,0.569c-1.766-0.063-3.514-0.295-5.178-0.913\n" + 
			"																						c-0.85-0.316-1.679-0.704-2.477-1.135c-0.586-0.317-0.963-0.864-1.172-1.512C0.149,32.706,0.06,32.646,0,32.577\n" + 
			"																						C0,24.772,0,16.966,0,9.161z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M143.65,24.142c0.365,0.2,0.727,0.405,1.094,0.6c2.411,1.281,4.828,2.549,7.23,3.846\n" + 
			"																						c0.712,0.385,1.337,0.894,1.755,1.617c0.756,1.309,0.48,2.746-0.712,3.681c-0.899,0.706-1.945,1.113-3.015,1.465\n" + 
			"																						c-1.615,0.532-3.268,0.891-4.969,0.968c-2.42,0.11-4.745-0.208-6.911-1.424c-2.55-1.431-5.141-2.788-7.716-4.174\n" + 
			"																						c-2.697-1.452-5.394-2.905-8.094-4.35c-1.686-0.903-3.384-1.783-5.066-2.694c-0.337-0.182-0.659-0.421-0.927-0.694\n" + 
			"																						c-0.648-0.658-0.61-1.534,0.05-2.181c0.693-0.68,1.592-0.937,2.486-1.189c1.792-0.506,3.626-0.631,5.482-0.604\n" + 
			"																						c2.041,0.03,4.083,0.016,6.125,0.004c1.588-0.009,3.118-0.296,4.524-1.076c0.737-0.409,1.143-1.009,1.11-1.894\n" + 
			"																						c-0.034-0.902-0.008-1.805-0.007-2.708c0.001-0.839-0.349-1.326-1.147-1.638c-1.225-0.479-2.511-0.603-3.802-0.608\n" + 
			"																						c-5.153-0.019-10.306-0.022-15.458,0.002c-1.288,0.006-2.58,0.104-3.798,0.609c-0.787,0.326-1.18,0.869-1.177,1.762\n" + 
			"																						c0.018,6.042-0.019,12.084,0.03,18.125c0.013,1.529-0.681,2.478-1.928,3.168c-1.242,0.687-2.582,1.099-3.978,1.266\n" + 
			"																						c-1.387,0.166-2.79,0.311-4.184,0.293c-2.231-0.029-4.423-0.394-6.469-1.355c-0.984-0.462-1.867-1.065-2.199-2.191\n" + 
			"																						c-0.077-0.262-0.114-0.546-0.114-0.819c-0.007-5.694-0.023-11.389,0.004-17.083c0.01-2.11,0.989-3.768,2.628-5.06\n" + 
			"																						c1.528-1.204,3.277-1.974,5.117-2.528c1.538-0.463,3.088-0.908,4.661-1.22c1.369-0.272,2.777-0.478,4.169-0.486\n" + 
			"																						c8.985-0.056,17.971-0.065,26.956-0.056c2.722,0.003,5.435,0.24,8.115,0.736c2.038,0.377,4.033,0.919,5.899,1.845\n" + 
			"																						c1.49,0.739,2.868,1.643,3.904,2.982c0.542,0.702,0.837,1.489,0.814,2.391c-0.019,0.75-0.026,1.501,0.001,2.25\n" + 
			"																						c0.055,1.558-0.569,2.832-1.612,3.929c-1.386,1.46-3.096,2.433-4.956,3.13c-1.178,0.442-2.404,0.754-3.608,1.126\n" + 
			"																						c-0.089,0.028-0.174,0.07-0.261,0.105C143.682,24.053,143.666,24.097,143.65,24.142z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M57.376,5.346c7.777,0,15.553-0.022,23.33,0.015c1.993,0.009,3.937,0.453,5.815,1.131\n" + 
			"																						c0.598,0.216,1.183,0.472,1.767,0.724C89.101,7.567,89.354,8.278,89,9.107c-0.311,0.729-0.906,1.144-1.616,1.326\n" + 
			"																						c-0.948,0.243-1.924,0.504-2.892,0.518c-5.51,0.082-11.022,0.094-16.533,0.128c-1.012,0.006-0.87-0.078-0.872,0.861\n" + 
			"																						c-0.004,2.097-0.006,4.194-0.001,6.291c0.012,4.54,0.015,9.081,0.055,13.621c0.01,1.086-0.457,1.857-1.295,2.457\n" + 
			"																						c-1.079,0.773-2.315,1.177-3.591,1.475c-2.211,0.517-4.454,0.629-6.703,0.39c-1.95-0.208-3.869-0.582-5.59-1.612\n" + 
			"																						c-1.152-0.689-1.749-1.648-1.704-3.05c0.064-1.998,0.022-3.999,0.015-5.999c-0.017-4.665-0.044-9.329-0.052-13.994\n" + 
			"																						c-0.001-0.408-0.189-0.443-0.511-0.443c-4.944,0.006-9.888,0.034-14.831-0.015c-1.434-0.014-2.87-0.228-4.297-0.414\n" + 
			"																						c-0.467-0.061-0.935-0.275-1.353-0.508c-0.68-0.379-1.086-0.943-1.067-1.788c0.018-0.831,0.469-1.345,1.118-1.726\n" + 
			"																						c0.822-0.483,1.732-0.721,2.669-0.88c2.254-0.38,4.524-0.409,6.802-0.405C43.628,5.355,50.502,5.346,57.376,5.346z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M160.238,20.832c0-3.597,0.04-7.194-0.019-10.79c-0.024-1.48,0.703-2.368,1.892-3.006\n" + 
			"																						c1.258-0.674,2.619-1.077,4.02-1.292c1.241-0.19,2.499-0.372,3.751-0.375c1.892-0.005,3.755,0.334,5.543,0.977\n" + 
			"																						c0.726,0.261,1.453,0.554,2.118,0.94c1.018,0.591,1.533,1.499,1.531,2.72c-0.014,7.249-0.015,14.498,0,21.747\n" + 
			"																						c0.003,1.311-0.674,2.159-1.717,2.792c-1.276,0.774-2.692,1.206-4.144,1.435c-1.35,0.213-2.728,0.32-4.096,0.334\n" + 
			"																						c-1.726,0.017-3.417-0.305-5.069-0.837c-0.917-0.295-1.81-0.626-2.591-1.196c-0.809-0.591-1.238-1.36-1.23-2.409\n" + 
			"																						C160.255,28.193,160.238,24.512,160.238,20.832z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M9.347,11.079C6.299,11.07,3.83,8.58,3.838,5.522C3.845,2.477,6.339-0.004,9.388,0\n" + 
			"																						c3.063,0.004,5.54,2.507,5.527,5.585C14.902,8.632,12.413,11.088,9.347,11.079z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M52.124,48.495c0,3.586,0,7.163,0,10.854c-2.553-2.758-5.055-5.461-7.63-8.244c0,2.66,0,5.217,0,7.809\n" + 
			"																						c-0.425,0-0.802,0-1.204,0c0-3.596,0-7.172,0-10.865c2.557,2.784,5.058,5.508,7.633,8.312c0-2.682,0-5.255,0-7.866\n" + 
			"																						C51.342,48.495,51.719,48.495,52.124,48.495z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M171.69,56.239c0-2.576,0-5.151,0-7.781c0.354,0,0.671-0.022,0.982,0.014\n" + 
			"																						c0.086,0.01,0.212,0.187,0.218,0.294c0.025,0.443,0.011,0.888,0.011,1.332c0,2.901,0,5.803,0,8.704c0,0.148,0,0.296,0,0.575\n" + 
			"																						c-2.583-2.791-5.081-5.492-7.656-8.275c0,2.662,0,5.229,0,7.82c-0.43,0-0.799,0-1.198,0c0-3.6,0-7.184,0-10.871\n" + 
			"																						c2.552,2.779,5.048,5.499,7.545,8.218C171.624,56.26,171.657,56.249,171.69,56.239z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M134.594,48.504c1.254,0,2.462-0.044,3.665,0.015c0.867,0.042,1.5,0.537,1.856,1.342\n" + 
			"																						c0.375,0.849,0.422,1.727,0.11,2.595c-0.346,0.963-1.132,1.349-2.093,1.454c-0.215,0.023-0.431,0.038-0.719,0.062\n" + 
			"																						c1.334,1.67,2.619,3.279,3.972,4.971c-0.561,0-1.025,0.019-1.486-0.013c-0.118-0.008-0.246-0.158-0.333-0.271\n" + 
			"																						c-1.108-1.44-2.208-2.886-3.312-4.33c-0.104-0.136-0.222-0.263-0.334-0.394c-0.035,0.012-0.071,0.023-0.106,0.035\n" + 
			"																						c0,1.637,0,3.274,0,4.944c-0.441,0-0.818,0-1.218,0C134.594,55.435,134.594,51.973,134.594,48.504z M135.813,53.051\n" + 
			"																						c0.754-0.05,1.474-0.059,2.182-0.156c0.719-0.099,1.073-0.492,1.146-1.217c0.035-0.351-0.001-0.72-0.068-1.069\n" + 
			"																						c-0.093-0.488-0.411-0.85-0.899-0.927c-0.769-0.121-1.55-0.163-2.36-0.241C135.813,50.685,135.813,51.815,135.813,53.051z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M202.515,52.579c0,0.399,0,0.754,0,1.159c-1.396,0-2.79,0-4.218,0c0,1.355,0,2.663,0,4.021\n" + 
			"																						c1.403,0,2.796,0,4.215,0c0,0.405,0,0.76,0,1.153c-1.826,0-3.646,0-5.488,0c0-3.473,0-6.936,0-10.427c1.824,0,3.634,0,5.479,0\n" + 
			"																						c0,0.358,0,0.709,0,1.116c-1.393,0-2.786,0-4.209,0c0,1.015,0,1.975,0,2.978C199.703,52.579,201.097,52.579,202.515,52.579z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M110.82,58.925c-1.824,0-3.625,0-5.462,0c0-3.475,0-6.948,0-10.446c1.812,0,3.613,0,5.45,0\n" + 
			"																						c0,0.362,0,0.725,0,1.138c-1.406,0-2.801,0-4.21,0c0,1.004,0,1.957,0,2.954c1.409,0,2.804,0,4.223,0c0,0.409,0,0.774,0,1.174\n" + 
			"																						c-1.404,0-2.787,0-4.204,0c0,1.343,0,2.651,0,4.009c1.392,0,2.786,0,4.204,0C110.82,58.17,110.82,58.536,110.82,58.925z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M78.11,49.662c-1.01,0-1.95,0-2.922,0c0-0.402,0-0.779,0-1.181c2.365,0,4.717,0,7.099,0\n" + 
			"																						c0,0.381,0,0.748,0,1.158c-0.947,0-1.897,0-2.904,0c0,3.111,0,6.18,0,9.279c-0.449,0-0.842,0-1.273,0\n" + 
			"																						C78.11,55.835,78.11,52.775,78.11,49.662z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M228.15,49.663c-1.031,0-1.973,0-2.949,0c0-0.397,0-0.775,0-1.18c2.36,0,4.714,0,7.1,0\n" + 
			"																						c0,0.378,0,0.757,0,1.178c-0.959,0-1.909,0-2.901,0c0,3.104,0,6.165,0,9.257c-0.424,0-0.806,0-1.25,0\n" + 
			"																						C228.15,55.853,228.15,52.782,228.15,49.663z\"/>\n" + 
			"																					<path fill=\"#FFFFFF\" d=\"M17.919,48.483c0.419,0,0.801,0,1.208,0c0,3.487,0,6.946,0,10.436c-0.403,0-0.785,0-1.208,0\n" + 
			"																						C17.919,55.454,17.919,51.985,17.919,48.483z\"/>\n" + 
			"																				</g>\n" + 
			"																				</svg>\n" + 
			"\n" + 
			"\n" + 
			"																		</div>\n" + 
			"																		<h2 style=\"text-align:center;font-weight:normal;font-family:Helvetica,Arial,sans-serif;font-size:23px; font-weight: 900; margin-bottom:10px;color:#205478;line-height:135%; padding: 30px 0 0 0;\">ITRIX</h2>\n" + 
			"																		<div style=\"text-align:center;font-family:Helvetica,Arial,sans-serif;font-size:15px;margin-bottom:0;color:#FFFFFF;line-height:135%;\">Sua fatura está disponível.</div>\n" + 
			"																	</td>\n" + 
			"																</tr>\n" + 
			"															</table>\n" + 
			"\n" + 
			"														</td>\n" + 
			"													</tr>\n" + 
			"												</table>\n" + 
			"												<!-- // FLEXIBLE CONTAINER -->\n" + 
			"											</td>\n" + 
			"										</tr>\n" + 
			"									</table>\n" + 
			"									<!-- // CENTERING TABLE -->\n" + 
			"								</td>\n" + 
			"							</tr>\n" + 
			"							<!-- // MODULE ROW -->\n" + 
			"						</table>\n" + 
			"						<!-- // END -->\n" + 
			"\n" + 
			"						<!-- EMAIL FOOTER // -->\n" + 
			"						<!--\n" + 
			"							The table \"emailBody\" is the email's container.\n" + 
			"							Its width can be set to 100% for a color band\n" + 
			"							that spans the width of the page.\n" + 
			"						-->\n" + 
			"						<table bgcolor=\"#E1E1E1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailFooter\">\n" + 
			"\n" + 
			"							<!-- FOOTER ROW // -->\n" + 
			"							<!--\n" + 
			"								To move or duplicate any of the design patterns\n" + 
			"								in this email, simply move or copy the entire\n" + 
			"								MODULE ROW section for each content block.\n" + 
			"							-->\n" + 
			"							<tr>\n" + 
			"								<td align=\"center\" valign=\"top\">\n" + 
			"									<!-- CENTERING TABLE // -->\n" + 
			"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" + 
			"										<tr>\n" + 
			"											<td align=\"center\" valign=\"top\">\n" + 
			"												<!-- FLEXIBLE CONTAINER // -->\n" + 
			"												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" + 
			"													<tr>\n" + 
			"														<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" + 
			"															<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" + 
			"																<tr>\n" + 
			"																	<td valign=\"top\" bgcolor=\"#E1E1E1\">\n" + 
			"\n" + 
			"																		<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:13px;color:#828282;text-align:center;line-height:120%;\">\n" + 
			"																			<div>iTrix - Copyright &#169; 2017 <a href=\"http://www.itrixti.com.br\" target=\"_blank\" style=\"text-decoration:none;color:#828282;\"><span style=\"color:#828282;\">Trix Tecnologia Inteligente</span></a>. All&nbsp;rights&nbsp;reserved.</div>\n" + 
			"																		</div>\n" + 
			"\n" + 
			"																	</td>\n" + 
			"																</tr>\n" + 
			"															</table>\n" + 
			"														</td>\n" + 
			"													</tr>\n" + 
			"												</table>\n" + 
			"												<!-- // FLEXIBLE CONTAINER -->\n" + 
			"											</td>\n" + 
			"										</tr>\n" + 
			"									</table>\n" + 
			"									<!-- // CENTERING TABLE -->\n" + 
			"								</td>\n" + 
			"							</tr>\n" + 
			"\n" + 
			"						</table>\n" + 
			"						<!-- // END -->\n" + 
			"\n" + 
			"					</td>\n" + 
			"				</tr>\n" + 
			"			</table>\n" + 
			"		</center>\n" + 
			"	</body>\n" + 
			"</html>";
}
