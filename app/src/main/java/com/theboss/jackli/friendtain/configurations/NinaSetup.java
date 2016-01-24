/*
 * 
 * File: NinaSetup.java
 * 
 * Copyright (C) 2015, Nuance Communications Inc. All Rights Reserved.
 * 
 */

package com.theboss.jackli.friendtain.configurations;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nuance.nina.mmf.CloudApplicationInfo;
import com.nuance.nina.mmf.LogLevel;
import com.nuance.nina.mmf.MMFController;
import com.nuance.nina.mmf.NinaConfiguration;

/**
 * Startup application class, this class setups Nina and the application with default values. They can be modified within the application in the Settings Activity.
 * The values are initialized from the Configurations class.
 *
 */
public class NinaSetup extends Application
{
	/**
	 * NMAID ID with time stamp
	 */
	private static String NMAID_ID = Configurations.NMAID_ID;
	/**
	 * NMAID Key as string
	 */
	private static String NMAID_KEY_STRING = Configurations.NMAID_KEY_STRING;
	/**
	 * NMAID Key
	 */
	private static byte[] NMAID_KEY = hexStringToByteArray(NMAID_KEY_STRING);
	/**
	 * Application Name
	 */
	private static String APPLICATION_NAME = Configurations.APPLICATION_NAME;
	/**
	 * Application version
	 */
	private static String APPLICATION_VERSION = Configurations.APPLICATION_VERSION;
	/**
	 * Company Name
	 */
	private static String COMPANY_NAME = Configurations.COMPANY_NAME;

	/**
	 * Server IP address to connect to
	 */
	private static String GATEWAY_HOST = Configurations.GATEWAY_HOST;
	/**
	 * Port number
	 */
	private static int GATEWAY_PORT = Configurations.GATEWAY_PORT;
	/**
	 * Gateway SSL 
	 */
	private static boolean GATEWAY_USE_SSL = Configurations.GATEWAY_USE_SSL;
	/**
	 * Gateway SSL certificate
	 */
	private static boolean GATEWAY_VERIFY_SSL_CERTIFICATE = Configurations.GATEWAY_VERIFY_SSL_CERTIFICATE;

	/**
	 * Server Application name
	 */
	private static String SERVER_APPLICATION_NAME = Configurations.SERVER_APPLICATION_NAME;

	/**
	 * Nina voice
	 */
	private static String TTS_VOICE_NAME = Configurations.TTS_VOICE_NAME;
	/**
	 * Nina locale
	 */
	private static String TTS_VOICE_LOCALE = Configurations.TTS_VOICE_LOCALE;

	/**
	 * Method to setup Nina with the configuration
	 */
	public static void fillNinaConfig()
	{
		if(null != NMAID_ID && null != NMAID_KEY && NMAID_KEY.length > 0 && NMAID_ID.length() > 0)
		{
			final NinaConfiguration config = MMFController.getInstance().getNinaConfiguration();
			config.setGatewayApplicationId(NMAID_ID);
			config.setGatewayApplicationKey(NMAID_KEY);

			config.setGatewayAddress(GATEWAY_HOST, GATEWAY_PORT);
			config.setGatewaySslEnabled(GATEWAY_USE_SSL);
			config.setGatewayValidateSslCertificate(GATEWAY_VERIFY_SSL_CERTIFICATE);

			config.setAdkApplicationName(SERVER_APPLICATION_NAME);

			config.setTtsDefaultLanguage(TTS_VOICE_LOCALE);
			config.setTtsDefaultVoice(TTS_VOICE_NAME);
			config.setCloudLogLevel(LogLevel.SEVERE);
		}else
		{
			Log.wtf("NinaSetup", "Invalid NMAID ID or KEY. Double check " +
					"settings in Configurations.java is valid before connect " +
					"to Nina Cloud services.");
		}
	}
	
	/**
	 * Convert a hex string to byte array
	 * Used to convert the NMAID Key string to byte array
	 * @param s
	 * @return byte[]
	 */
	public static byte[] hexStringToByteArray(String NMAIDKeyHexString)
    {
	    byte[] result = null;
	    
	    if(null != NMAIDKeyHexString && NMAIDKeyHexString.length() > 0 )
	    {
	        String[] hexStrings = NMAIDKeyHexString.trim().split(" ");
	        result = new byte[hexStrings.length];
	        
	        for(int index = 0; index <hexStrings.length; index++)
	        {
	            result [index ] = Integer.decode(hexStrings[index]).byteValue();
	        }	        
	    }else
	    {
	        Log.wtf("hexStringToByteArray", "Invalid NAMID KEY. Check if " +
					"ApplicationSettings.java has the correct values.");
	    }
	    return result;
    }
	 
	/**
	 * This method creates an instance of CloudApplicationInfo linked to the company, application and version. The variables are initialized in the Configurations class,
	 * the user can manually change the values as long as they are not null or an empty string.
	 * That class contains the information necessary to connect to a server.
	 * 
	 * @return CloudApplicationInfo
	 */
	public static CloudApplicationInfo getCloudApplicationInfo()
	{
		return new CloudApplicationInfo(APPLICATION_NAME, APPLICATION_VERSION, COMPANY_NAME);
	}

	@Override
	public void onCreate()
	{
		fillNinaConfig();
	}

	static NinaSetup fromContext(Context context)
	{
		return (NinaSetup)context.getApplicationContext();
	}

	/**
	 * Get Name ID
	 * @return NMAID_ID
	 */
	public static String getNmaidId()
	{
		return NMAID_ID;
	}
	/**
	 * Get NMAID Key String
	 * @return nmaid_key_string
	 */
	public static String getNmaid_key_string() {
		return NMAID_KEY_STRING;
	}
	/**
	 * Get Name KEY
	 * @return NMAID_KEY
	 */
	public static byte[] getNmaidKey()
	{
		return NMAID_KEY;
	}
	/**
	 * Get Application Name
	 * @return APPLICATION_NAME
	 */
	public static String getApplicationName()
	{
		return APPLICATION_NAME;
	}
	/**
	 * Get Application Version
	 * @return APPLICATION_VERSION
	 */
	public static String getApplicationVersion()
	{
		return APPLICATION_VERSION;
	}
	/**
	 * Get Company Name
	 * @return COMPANY_NAME
	 */
	public static String getCompanyName()
	{
		return COMPANY_NAME;
	}
	/**
	 * Get Gateway host
	 * @return GATEWAY_HOST
	 */
	public static String getGatewayHost()
	{
		return GATEWAY_HOST;
	}

	public static void setGatewayHost(String gatewayHost)
	{
		GATEWAY_HOST = gatewayHost;
	}

	/**
	 * Get Gateway Port
	 * @return GATEWAY_PORT
	 */
	public static int getGatewayPort()
	{
		return GATEWAY_PORT;
	}

	/**
	 * Set port
	 * @param gatewayPort
	 */
	public static void setGatewayPort(int gatewayPort)
	{
		GATEWAY_PORT = gatewayPort;
	}
	/**
	 * Set NMAID key String
	 * @param nmaid_key_string
	 */
	public static void setNmaid_key_string(String nmaid_key_string) {
		NinaSetup.NMAID_KEY_STRING = nmaid_key_string;
	}
	/**
	 * Checks if gateway is using SSL
	 * @return GATEWAY_USE_SSL
	 */
	public static boolean isGatewayUseSsl()
	{
		return GATEWAY_USE_SSL;
	}

	/**
	 * Sets gateway SSL
	 * @param gatewayUseSsl
	 */
	public static void setGatewayUseSsl(boolean gatewayUseSsl)
	{
		GATEWAY_USE_SSL = gatewayUseSsl;
	}

	/**
	 * Checks if gateway is SSL Certificate
	 * @return
	 */
	public static boolean isGatewayVerifySslCertificate()
	{
		return GATEWAY_VERIFY_SSL_CERTIFICATE;
	}

	/**
	 * Sets gateway SSL Certificate
	 * @param gatewayVerifySslCertifiacte
	 */
	public static void setGatewayVerifySslCertificate(boolean gatewayVerifySslCertificate)
	{
		GATEWAY_VERIFY_SSL_CERTIFICATE = gatewayVerifySslCertificate;
	}

	/**
	 * Get Server Application name
	 * @return SERVER_APPLICATION_NAME
	 */
	public static String getServerApplicationName()
	{
		return SERVER_APPLICATION_NAME;
	}

	/**
	 * Sets Server Apllication name
	 * @param serverApplicationName
	 */
	public static void setServerApplicationName(String serverApplicationName)
	{
		SERVER_APPLICATION_NAME = serverApplicationName;
	}

	/**
	 * Get Nina voice
	 * @return tTS_VOICE_NAME
	 */
	public static String getTTS_VOICE_NAME()
	{
		return TTS_VOICE_NAME;
	}
	/**
	 * Set Nina voice
	 * @param tTS_VOICE_NAME
	 */
	public static void setTTS_VOICE_NAME(String tTS_VOICE_NAME)
	{
		TTS_VOICE_NAME = tTS_VOICE_NAME;
	}
	/**
	 * Get Nina locale
	 * @return TTS_VOICE_LOCALE
	 */
	public static String getTTS_VOICE_LOCALE()
	{
		return TTS_VOICE_LOCALE;
	}

	/**
	 * Set Nina locale
	 * @param tTS_VOICE_LOCALE
	 */
	public static void setTTS_VOICE_LOCALE(String tTS_VOICE_LOCALE)
	{
		TTS_VOICE_LOCALE = tTS_VOICE_LOCALE;
	}

	/**
	 * Set name ID
	 * @param nmaidId
	 */
	public static void setNmaidId(String nmaidId)
	{
		NMAID_ID = nmaidId;
	}

	/**
	 * Set name Key
	 * @param nmaidKey
	 */
	public static void setNmaidKey(byte[] nmaidKey)
	{
		NMAID_KEY = nmaidKey;
	}

	/**
	 * Set application Name
	 * @param applicationName
	 */
	public static void setApplicationName(String applicationName)
	{
		APPLICATION_NAME = applicationName;
	}

	/**
	 * Set application Version
	 * @param applicationVersion
	 */
	public static void setApplicationVersion(String applicationVersion)
	{
		APPLICATION_VERSION = applicationVersion;
	}

	/**
	 * Set company name
	 * @param companyName
	 */
	public static void setCompanyName(String companyName)
	{
		COMPANY_NAME = companyName;
	}

	/**
	 * Get the boolean to the visibility
	 * @return
	 */
	public static boolean isActivityVisible() {
		return activityVisible;
	}  

	/**
	 * Sets the boolean to true on activity visible
	 */
	public static void activityResumed() {
		activityVisible = true;
	}

	/**
	 * Sets the boolean to false on activity not visible
	 */
	public static void activityPaused() {
		activityVisible = false;
	}

	/**
	 * Variable to determine the application visibility
	 */
	private static boolean activityVisible;
}
