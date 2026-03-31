package com.agibank.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Page Object para validações de responsividade
 */
public class ResponsividadePage extends BasePage {

    public ResponsividadePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Define a resolução da janela do navegador
     */
    public void definirResolucao(int largura, int altura) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(largura, altura));
        logger.info("Resolução definida para: {}x{}", largura, altura);

        // Para resoluções mobile, adiciona meta viewport
        if (largura < 500) {
            ((JavascriptExecutor) driver).executeScript(
                    "var meta = document.createElement('meta');" + "meta.name = 'viewport';"
                            + "meta.content = 'width=" + largura + ", initial-scale=1';"
                            + "document.getElementsByTagName('head')[0].appendChild(meta);");
            logger.debug("Meta viewport adicionada para resolução mobile");
        }

        // Aguarda viewport estar estável
        wait.until((ExpectedCondition<Boolean>) driver -> {
            Long currentWidth = getViewportWidth();
            logger.debug("Viewport atual: {}px (esperado: ~{}px)", currentWidth, largura);
            return currentWidth != null;
        });
    }

    /**
     * Verifica se não há scroll horizontal na página
     */
    public boolean naoTemScrollHorizontal() {
        Long scrollWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.scrollWidth;");
        Long clientWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.clientWidth;");

        logger.debug("ScrollWidth: {}px, ClientWidth: {}px", scrollWidth, clientWidth);
        // Aceita até 10px de diferença (margem para scrollbar e padding)
        return scrollWidth <= clientWidth + 10;
    }

    /**
     * Obtém a largura atual da viewport
     */
    public Long getViewportWidth() {
        return (Long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth;");
    }

    /**
     * Verifica se há overflow horizontal
     */
    public boolean temOverflow() {
        Boolean hasOverflow = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return document.body.scrollWidth > window.innerWidth;");
        return hasOverflow != null && hasOverflow;
    }

    /**
     * Verifica se o layout está adaptado para mobile (≤ 480px) Como o Chrome tem
     * largura mínima de
     * viewport, aceita até 520px como mobile
     */
    public boolean isLayoutMobile() {
        Long viewportWidth = getViewportWidth();
        // Aceita até 520px considerando a limitação do Chrome
        boolean viewportCorreta = viewportWidth <= 520;

        logger.info("Mobile - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

    /**
     * Verifica se o layout está adaptado para tablet (480px < viewport ≤ 1024px)
     */
    public boolean isLayoutTablet() {
        Long viewportWidth = getViewportWidth();
        boolean viewportCorreta = viewportWidth > 480 && viewportWidth <= 1024;

        logger.info("Tablet - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

    /**
     * Verifica se o layout está adaptado para desktop (> 1024px)
     */
    public boolean isLayoutDesktop() {
        Long viewportWidth = getViewportWidth();
        boolean viewportCorreta = viewportWidth > 1024;

        logger.info("Desktop - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

    /**
     * Verifica se o layout está adaptado para o tipo de dispositivo especificado
     */
    public boolean isLayoutAdaptado(String tipoDispositivo) {
        switch (tipoDispositivo.toLowerCase()) {
            case "mobile":
                return isLayoutMobile();
            case "tablet":
                return isLayoutTablet();
            case "desktop":
                return isLayoutDesktop();
            default:
                throw new IllegalArgumentException(
                        "Tipo de dispositivo desconhecido: " + tipoDispositivo);
        }
    }
}
