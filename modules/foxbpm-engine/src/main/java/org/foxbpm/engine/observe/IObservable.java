package org.foxbpm.engine.observe;

/**
 * 被观察者接口
 * 
 * @author zengxianping
 *
 */
public interface IObservable {
//	/**
//	 * 添加一个观察者
//	 * 
//	 * @param observer
//	 */
//	public void addObserver(IObserver observer);
//
//	/**
//	 * 删除一个观察者
//	 * 
//	 * @param observer
//	 */
//	public void deleteObserver(IObserver observer);

	/**
	 * 通知所有的观察者
	 * 
	 * @param arg
	 *            附带参数
	 */
	public void notifyObservers(Object arg);
}
