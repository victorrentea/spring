//package victor.training.spring.web.controller;
//
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class MyPreauthenticatedUserFilter extends AbstractPreAuthenticatedProcessingFilter {
//   @Override
//   protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//      return new MyPrinciplaAccesibleLaterViaGetPrincipal(request.getHeader("X-USER"));
//   }
//
//   @Override
//   protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//      return request.getHeader("X-PERMISSIONS");
//      return null;
//   }
//}
