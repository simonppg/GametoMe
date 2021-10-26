/*
 * Copyright (C) 2014 simonppg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gametome.personajes;

import java.awt.image.BufferedImage;

/**
 * Almacena las imagenes para los personajes.
 * @author simonppg
 */
public class Imagen {
    /**
     * Imagen.
     */
    private BufferedImage imagen;
    /**
     * Ancho de la imagen.
     */
    private int ImgAncho;
    /**
     * Alto de la imagen.
     */
    private int ImgAlto;

    public Imagen() {
    }

    /**
     * Get the value of imagen
     *
     * @return the value of imagen
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * Set the value of imagen
     *
     * @param imagen new value of imagen
     */
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
        this.ImgAlto = this.imagen.getHeight();
        this.ImgAncho = this.imagen.getWidth();
    }

    public int getImgAncho() {
        return ImgAncho;
    }

    public int getImgAlto() {
        return ImgAlto;
    }
}
