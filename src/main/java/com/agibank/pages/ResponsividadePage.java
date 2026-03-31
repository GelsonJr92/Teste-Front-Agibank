package com.agibank.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import lombok.extern.slf4j.Slf4j;

/**
 * Page Object para validações de responsividade
 */
@Slf4j
public class ResponsividadePage extends BasePage {

    public ResponsividadePage(WebDriver driver) {
        super(driver);
    }

    public void definirResolucao(int largura, int altura) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(largura, altura));
        log.info("Resolução definida para: {}x{}", largura, altura);

        // Para resoluções mobile, adiciona meta viewport
        if (largura < 500) {
            ((JavascriptExecutor) driver).executeScript(
                    "var meta = document.createElement('meta');" + "meta.name = 'viewport';"
                            + "meta.content = 'width=" + largura + ", initial-scale=1';"
                            + "document.getElementsByTagName('head')[0].appendChild(meta);");
            log.debug("Meta viewport adicionada para resolução mobile");
        }

        // Aguarda viewport estar estável
        wait.until((ExpectedCondition<Boolean>) driver -> {
            Long currentWidth = getViewportWidth();
            log.debug("Viewport atual: {}px (esperado: ~{}px)", currentWidth, largura);
            return currentWidth != null;
        });
    }

    public boolean naoTemScrollHorizontal() {
        Long scrollWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.scrollWidth;");
        Long clientWidth = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.clientWidth;");

        log.debug("ScrollWidth: {}px, ClientWidth: {}px", scrollWidth, clientWidth);
        // Aceita até 10px de diferença (margem para scrollbar e padding)
        return scrollWidth <= clientWidth + 10;
    }

    public Long getViewportWidth() {
        return (Long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth;");
    }

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

        log.info("Mobile - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

    public boolean isLayoutTablet() {
        Long viewportWidth = getViewportWidth();
        boolean viewportCorreta = viewportWidth > 480 && viewportWidth <= 1024;

        log.info("Tablet - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

    public boolean isLayoutDesktop() {
        Long viewportWidth = getViewportWidth();
        boolean viewportCorreta = viewportWidth > 1024;

        log.info("Desktop - Viewport: {}px, Válido: {}", viewportWidth, viewportCorreta);
        return viewportCorreta;
    }

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
