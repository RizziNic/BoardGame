package com.company.Agents;

public interface Shooter {
    public enum Projectile {
        PALLONI, /* dist max: 3 danno max: 10 */
        BIGLIE   /* dist max : 7 danno max: 5 */
    }
    public char chooseProjectile();

}
