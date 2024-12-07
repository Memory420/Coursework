package com.memory.Utils;

/**
 * Перечисление {@code DriverState} описывает возможные состояния водителя в автопарке.
 * <p>
 * Водитель может находиться в одном из следующих состояний:
 * <ul>
 *   <li><strong>NOT_HIRED</strong> - водитель не нанят.</li>
 *   <li><strong>WORKING</strong> - водитель работает в данный момент.</li>
 *   <li><strong>RESTING</strong> - водитель отдыхает (включая обед).</li>
 * </ul>
 * <p>
 */
public enum DriverState {
    /** Водитель не нанят. */
    NOT_HIRED,

    /** Водитель работает в данный момент. */
    WORKING,

    /** Водитель отдыхает (включая обед). */
    RESTING
}

