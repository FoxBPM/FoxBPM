package org.foxbpm.engine.observe;

/**
 * 观察者接口
 * 
 * @author zengxianping
 *
 */
public interface IObserver {
	/**
	 * 当被观察者改变的时候，观察者需要做的更新操作
	 * 
	 * @param observable
	 *            被观察者对象
	 * @param arg
	 *            附带参数
	 */
	public void update(IObservable observable, Object arg);
}
