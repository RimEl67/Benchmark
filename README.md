# 🚀 Benchmark de performances des Web Services REST  
# par: Rim EL ABBASSI — Brahim EL MAJDAOUI


Ce dépôt présente l’étude comparative de plusieurs implémentations de services REST.  
L’objectif est d’analyser leur performance, leur consommation de ressources, ainsi que leur comportement sous différentes charges applicatives.

---

## 📌 1. Objectif du Benchmark

L’étude vise à :

- Comparer plusieurs approches pour exposer des API REST (frameworks, variantes Spring, etc.).
- Mesurer leur capacité à gérer des scénarios variés : lecture intensive, jointures, écritures, payloads lourds.
- Évaluer l’impact sur les ressources système (CPU, mémoire, threads, pool de connexions).
- Identifier les points forts, limites et domaines d’utilisation recommandés pour chaque technologie.

---

## 🧩 2. Architecture et Pile Technologique

Le benchmark repose sur une architecture standard composée de :

- **Un service Web** implémenté en plusieurs variantes (par exemple Jersey, Spring MVC, Spring Data REST…).
- **Une base de données PostgreSQL**.
- **Un outil de test de charge** pour générer des scénarios de charge réalistes.
- **Un système de monitoring** pour l’observation JVM et système.

### Technologies utilisées

- **Java 21 — HotSpot VM**
- **Spring Boot / JAX-RS (selon variante)**
- **PostgreSQL**
- **Docker & Docker Compose**
- **JMeter** pour les tests de charge
- **Prometheus + Grafana** pour le monitoring
- **HikariCP** pour la gestion des connexions SQL

---

## 📂 3. Scénarios de Test

Plusieurs scénarios couvrant les usages les plus courants ont été définis :

### 🔹 3.1. READ-heavy  
Tests de forte sollicitation en lecture, incluant la récupération d’entités avec relations.

### 🔹 3.2. JOIN-filter  
Scénarios ciblant la performance des requêtes avec filtrage + jointures SQL.

### 🔹 3.3. MIXED  
Alternance d’opérations CREATE, READ, UPDATE, DELETE, reflétant un usage applicatif réel.

### 🔹 3.4. HEAVY-body  
Tests impliquant des payloads volumineux (JSON large).

---

## 📊 4. Métriques Observées

Les tests ont permis de collecter plusieurs indicateurs essentiels :

### 🔸 Performance API
- **Débit (RPS)** : nombre de requêtes traitées par seconde.
- **Latences** : p50, p95, p99.
- **Taux d’erreurs** : HTTP 4xx / 5xx.

### 🔸 Ressources JVM
- Utilisation **CPU**
- Consommation **heap**
- Temps **GC**
- **Threads actifs**
- **Connexions Hikari** utilisées

### 🔸 Observation système
- I/O disque
- Réseau
- Charge CPU globale

---

## 🧪 5. Méthodologie

Les tests suivent une méthodologie reproductible :

1. **Déploiement** de chaque variante dans un environnement isolé Docker.
2. **Préparation des données** (dataset initial).
3. **Exécution automatisée** des scénarios JMeter.
4. **Instrumentation** via Prometheus.
5. **Analyse** via des dashboards Grafana dédiés.
6. **Comparaison qualitative et quantitative** des comportements.

---

## 📌 6. Critères de Comparaison

Chaque variante a été comparée selon :

- **Performance brute** (débit et latence)
- **Stabilité sous charge**
- **Simplicité de développement**
- **Facilité d’exposition des relations**
- **Consommation CPU/RAM**
- **Robustesse face aux payloads lourds**
- **Risque de N+1 / lazy-loading**

---


