package com.linktop.cloud.entranceguardcore.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.aop.handler.AspectHandler;
import com.linktop.cloud.entranceguardcore.aop.vo.user.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
	private static Logger log = LoggerFactory.getLogger(UserAspect.class);

	@Autowired
	AspectHandler aspectHandler;
	
	@Pointcut(value = "execution(* com.linktop.cloud.entranceguardcore.service.UsersService.add(..))")
	public void pointCutAdd() {
	}

	@Around("pointCutAdd()")
	public Object aroundUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
        Object[] args = joinPoint.getArgs();

    	UserAddVO vo = new UserAddVO();
    	try {
			vo.setArgObj(args);
			vo.setRetObj(null);
			aspectHandler.handleBefore(vo);
		} catch (Exception e) {

		}
        
        try {

            result = joinPoint.proceed(args);
            
        } catch(Exception e) {
        	
        	throw e;
        	
        } finally {
        	
        	try {
    			vo.setArgObj(args);
    			vo.setRetObj(result);
    			aspectHandler.handleAfter(vo);
    		} catch (Exception e) {
				e.printStackTrace();
				log.info("exception:{}", e);
    		}        	
        }
        
        return result;  
	}

	@Pointcut(value = "execution(* com.linktop.cloud.entranceguardcore.service.UsersService.delete(..))")
	public void pointCutDelete() {
	}

	@Around("pointCutDelete()")
	public Object aroundDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
        Object[] args = joinPoint.getArgs();  
        
    	UserDeleteVO vo = new UserDeleteVO();

    	
        try {
        	vo.setArgObj(args);
        	vo.setRetObj(null);
        	aspectHandler.handleBefore(vo);
        } catch(Exception e) {
        	
        }
   	
        try {
            result = joinPoint.proceed(args);          	
        } catch(Exception e) {
        	
        	throw e;
        	
        } finally {
            
        	try {
                vo.setArgObj(args);
            	vo.setRetObj(result);
            	aspectHandler.handleAfter(vo);
            } catch(Exception e) {
            	
            }        	
        }

        return result;  
	}

	@Pointcut(value = "execution(* com.linktop.cloud.entranceguardcore.service.UsersService.update(..))")
	public void pointCutUpdate() {
	}

	@Around("pointCutUpdate()")
	public Object aroundUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
        Object[] args = joinPoint.getArgs();  
        
        UserUpdateVO vo = new UserUpdateVO();
    	try {
			vo.setArgObj(args);
			vo.setRetObj(null);
			aspectHandler.handleBefore(vo);
		} catch (Exception e) {

		}
   	
        try {

            result = joinPoint.proceed(args);          	
           
        } catch(Exception e) {
        	
        	throw e;
        	
        } finally {
            
        	try {
    			vo.setArgObj(args);
    			vo.setRetObj(result);
    			aspectHandler.handleAfter(vo);
    		} catch (Exception e) {
    			e.printStackTrace();
    			log.info("exception:{}", JSON.toJSONString(e));
    		}
        }
        
        return result;  
	}

	@Pointcut(value = "execution(* com.linktop.cloud.entranceguardcore.service.UsersService.batchDel(..))")
	public void pointCutBatchDel() {
	}

	@Around("pointCutBatchDel()")
	public Object aroundBatchDel(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		Object[] args = joinPoint.getArgs();

		UserBatchDelVO vo = new UserBatchDelVO();
		try {
			vo.setArgObj(args);
			vo.setRetObj(null);
			aspectHandler.handleBefore(vo);
		} catch (Exception e) {

		}

		try {

			result = joinPoint.proceed(args);

		} catch(Exception e) {

			throw e;

		} finally {

			try {
				vo.setArgObj(args);
				vo.setRetObj(result);
				aspectHandler.handleAfter(vo);
			} catch (Exception e) {

			}
		}

		return result;
	}


	@Pointcut(value = "execution(* com.linktop.cloud.entranceguardcore.service.UsersService.getPage(..))")
	public void pointCutGetPage() {
	}

	@Around("pointCutGetPage()")
	public Object aroundGetPage(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		Object[] args = joinPoint.getArgs();

        UserGetPageVO vo = new UserGetPageVO();
		try {
			vo.setArgObj(args);
			vo.setRetObj(null);
			aspectHandler.handleBefore(vo);
		} catch (Exception e) {

		}

		try {

			result = joinPoint.proceed(args);

		} catch(Exception e) {

			throw e;

		} finally {

			try {
				vo.setArgObj(args);
				vo.setRetObj(result);
				aspectHandler.handleAfter(vo);
			} catch (Exception e) {

			}
		}

		return result;
	}

}
