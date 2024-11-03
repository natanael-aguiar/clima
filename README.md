# Clima

## Descrição

O App de Clima é um aplicativo Android desenvolvido como parte de uma atividade prática orientada (APO) para a disciplina de Programação de Dispositivos Móveis. O objetivo do projeto é proporcionar uma experiência de desenvolvimento mobile real, focada em boas práticas de programação, uso de componentes do Material Design e consumo de uma API de previsão do tempo.

## Tecnologias Utilizadas

- **Linguagem**: Java
- **IDE**: Android Studio
- **API de Clima**: [HG Brasil Weather API](https://console.hgbrasil.com/documentation/weather)
- **Componentes**:
  - RecyclerView
  - CardView
  - TabLayout
  - ViewPager
  - Fragmentos
  - Toolbar com menu de navegação
  - FloatingActionButton
  - Google Maps
  - ZXing para leitura de QR Codes

## Estrutura do Projeto

O aplicativo é composto por várias telas e componentes, conforme solicitado na atividade:

1. **Splash Screen**: Tela de abertura exibida por 3 segundos com um logo do aplicativo.
2. **Tela Principal com TabLayout**:
    - **Aba 1 (Previsão do Tempo)**: Mostra uma lista de previsões do tempo em cards usando RecyclerView e CardView. Os dados são obtidos da API da HG Brasil por meio do `woeid` fixo (457197), que representa a cidade de Francisco Beltrão.
    - **Aba 2 (Mapa)**: Mostra um mapa com um marcador fixo na localização da cidade de consulta.
3. **Tela de Sobre**: Exibe informações pessoais como nome, RA e curso.

## Funcionalidades

- **Splash Screen**: Exibe uma imagem de introdução por 3 segundos antes de redirecionar o usuário para a tela principal.
- **Previsão do Tempo**: Consome a API da HG Brasil para buscar as previsões de tempo. Como a API gratuita não suporta consultas por latitude/longitude, é utilizado o `woeid` fixo para a cidade.
- **Mapa**: Mostra a localização da cidade usando o Google Maps.
- **QR Code**: O FloatingActionButton permite a leitura de QR Codes para alterar a localização (não integrada à busca de dados devido às limitações da API gratuita).
- **Menu Lateral**: Implementado com DrawerLayout e NavigationView para navegação.

## Configuração e Instalação

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/seu-usuario/ClimaFacil.git
   ```

2. **Abra o projeto no Android Studio**.
3. **Instale as dependências**:
   Verifique se as dependências do `build.gradle` incluem:

   ```gradle
   implementation 'com.squareup.retrofit2:retrofit:2.9.0'
   implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
   implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
   implementation 'com.google.android.gms:play-services-maps:18.0.2'
   ```

4. **Execute o aplicativo**:
   Conecte um dispositivo físico ou use um emulador para executar o projeto.

## Estrutura do Código

### Principais Arquivos

- `MainActivity.java`: Gerencia a navegação entre as abas e integrações gerais do aplicativo.
- `WeatherFragment.java`: Consome a API de clima e exibe os dados em uma lista.
- `MapFragment.java`: Exibe o mapa com o marcador fixo.
- `WeatherService.java`: Interface de Retrofit para realizar chamadas à API.

### Exemplo de Chamada à API

O `WeatherFragment` faz uma chamada à API da seguinte forma:

```java
Call<WeatherResponse> call = weatherService.getWeather(457197); // woeid fixo para Francisco Beltrão
call.enqueue(new Callback<WeatherResponse>() {
    @Override
    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            // Atualiza a interface com os dados recebidos
        }
    }

    @Override
    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
        Log.e("WeatherFragment", "Erro ao carregar dados", t);
    }
});
```

## Testes Realizados

- **QR Code**: Testado com diferentes entradas para simular leituras de coordenadas (as coordenadas não são usadas para consulta devido à limitação da API).
- **Mapa**: Verificado se o marcador é exibido corretamente na cidade definida.
- **Lista de Previsão**: Testes de renderização dos cards e resposta da API confirmada.

## Requisitos da Atividade Atendidos

- Implementação de RecyclerView e CardView.
- Uso de TabLayout com abas.
- Menu de navegação lateral.
- FloatingActionButton para escanear QR Codes.
- Tela de "Sobre" com informações pessoais.

## Conclusão

O App de Clima é um projeto que demonstra o uso prático de APIs, componentes do Material Design e boas práticas de desenvolvimento em Java. Cumpre os requisitos da atividade proposta, proporcionando uma interface funcional e amigável.

---

**Autor**: Natanael Oliveira de Aguiar  
**RA**: 09048450  
**Curso**: Análise e desenvolvimento de sistemas - UNIPAR EAD
