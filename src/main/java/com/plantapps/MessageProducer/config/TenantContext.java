package com.plantapps.MessageProducer.config;

public class TenantContext
{
	private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    // Set the tenant ID for the current thread
    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId);
    }

    // Get the tenant ID for the current thread
    public static String getTenantId() {
        return currentTenant.get();
    }

    // Clear the tenant context after processing the request to avoid memory leaks
    public static void clear() {
        currentTenant.remove();
    }
}
