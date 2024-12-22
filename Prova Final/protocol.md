# Protocolo de Comunicação entre Servidor e Filósofos

- **HELLO**: O cliente solicita uma conexão.
  - **Servidor Responde**: `HI <ID>` (onde `<ID>` é o identificador único do filósofo).


1. **THINKING**: Notifica que o filósofo começou a pensar.
   - **Formato**: `THINKING <ID>`
   - **Resposta**: `ACK THINKING`

2. **REQUEST_FORKS**: Solicita acesso aos garfos para comer.
   - **Formato**: `REQUEST_FORKS <ID>`
   - **Resposta**:
     - `GRANTED`: Se os garfos estão disponíveis.
     - `DENIED`: Se os garfos estão ocupados.

3. **EATING**: Notifica que o filósofo começou a comer.
   - **Formato**: `EATING <ID>`
   - **Resposta**: `ACK EATING`

4. **RELEASE_FORKS**: Devolve os garfos ao servidor.
   - **Formato**: `RELEASE_FORKS <ID>`
   - **Resposta**: `ACK RELEASED`

5. **DISCONNECT**: Notifica o encerramento da conexão.
   - **Formato**: `DISCONNECT <ID>`
   - **Resposta**: `BYE`
