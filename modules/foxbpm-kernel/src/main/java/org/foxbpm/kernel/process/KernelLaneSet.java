package org.foxbpm.kernel.process;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lane Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.LaneSet#getLanes <em>Lanes</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.LaneSet#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.Bpmn2Package#getLaneSet()
 * @model extendedMetaData="name='tLaneSet' kind='elementOnly'"
 * @generated
 */
public interface KernelLaneSet extends KernelBaseElement {
    /**
     * Returns the value of the '<em><b>Lanes</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.bpmn2.Lane}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lanes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lanes</em>' containment reference list.
     * @see org.eclipse.bpmn2.Bpmn2Package#getLaneSet_Lanes()
     * @model containment="true" ordered="false"
     *        extendedMetaData="kind='element' name='lane' namespace='http://www.omg.org/spec/BPMN/20100524/MODEL'"
     * @generated
     */
    List<KernelLane> getLanes();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.bpmn2.Bpmn2Package#getLaneSet_Name()
     * @model ordered="false"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.bpmn2.LaneSet#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // LaneSet
