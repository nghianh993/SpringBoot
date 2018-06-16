package fis.htkh.configs;

import fis.htkh.entities.Permission;
import fis.htkh.services.IPermissionService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<String, List<ConfigAttribute>> resourceMap = null;
    private PathMatcher pathMatcher = new AntPathMatcher();

    private IPermissionService permissionService;

    public CustomSecurityMetadataSource(IPermissionService permissionService) {
        super();
        this.permissionService = permissionService;
    }

    private Map<String, List<ConfigAttribute>> loadResourceMatchAuthority(String url) {
        List<Permission> lstPermission = permissionService.getPermissionByLink(url);
        Map<String, List<ConfigAttribute>> map = new HashMap<>();
        if (lstPermission != null && !lstPermission.isEmpty()) {
            for (Permission permission : lstPermission) {
                List<ConfigAttribute> list = new ArrayList<>();
                ConfigAttribute config = new SecurityConfig(permission.getLink());
                list.add(config);
                map.put(permission.getLink(), list);
            }
        }
        return map;
    }

    public List<ConfigAttribute> getAttributes(Object object) {
        String url = ((FilterInvocation) object).getRequestUrl();
        resourceMap = loadResourceMatchAuthority(url);
        for (Map.Entry<String, List<ConfigAttribute>> resURL : resourceMap.entrySet()) {
            if (pathMatcher.match(resURL.getKey(), url)) {
                System.out.println("URL :" + "'" + url + "'");
                System.out.println("Danh sach permission URL: " + resURL.getValue());
                return resURL.getValue();
            }
        }
        return resourceMap.get(url);
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}