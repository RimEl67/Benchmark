# 🚀 Benchmark de performances des Web Services REST - par: Rim EL ABBASSI — Brahim EL MAJDAOUI


---

## 🧩 T0 — Environnement de test

| Élément | Configuration |
|--------|--------------|
| Matériel | Intel i7-8750H (6C/12T, 2.2–4.1 GHz) – 16 Go RAM |
| OS | Windows 11 Pro 64-bit / Ubuntu 22.04 LTS |
| Java | OpenJDK 21 (HotSpot VM) |
| Docker | Docker 26.x + Compose 2.20.x |
| Base de données | PostgreSQL 14.x |
| Outils de test | JMeter 5.6.3 |
| Monitoring | Prometheus 2.51 • Grafana 11 • InfluxDB 2.7 |
| JVM | Xms=2G – Xmx=3G – G1GC |
| HikariCP | min=10 • max=20 • timeout=30s |

---

## 📈 T2 — Résultats globaux par scénario

### 🔹 Scénario READ-heavy (lecture + relations)

| Mesure | Jersey (A) | RestController (C) | Spring Data REST (D) |
|--------|------------|--------------------|-----------------------|
| RPS | 710/s | 540/s | 420/s |
| p50 | 18 ms | 41 ms | 60 ms |
| p95 | 58 ms | 180 ms | 295 ms |
| p99 | 99 ms | 420 ms | 911 ms |
| Erreurs | 0.01% | 0% | 0.02% |

---

### 🔹 Scénario JOIN-filter

| Mesure | A | C | D |
|--------|---|---|---|
| RPS | 255/s | 180/s | 140/s |
| p50 | 14 ms | 23 ms | 39 ms |
| p95 | 52 ms | 88 ms | 142 ms |
| p99 | 65 ms | 159 ms | 298 ms |
| Erreurs | 0% | 0.12% | 0.21% |

---

### 🔹 Scénario MIXED (lecture/écriture)

| Mesure | A | C | D |
|--------|---|---|---|
| RPS | 120/s | 115/s | 93/s |
| p50 | 11 ms | 19 ms | 25 ms |
| p95 | 42 ms | 59 ms | 120 ms |
| p99 | 92 ms | 185 ms | 313 ms |
| Erreurs | 0.3% | 1.1% | 2.5% |

---

### 🔹 Scénario HEAVY-body (payload massif)

| Mesure | A | C | D |
|--------|---|---|---|
| RPS | 50/s | 45/s | 32/s |
| p50 | 45 ms | 67 ms | 90 ms |
| p95 | 160 ms | 330 ms | 520 ms |
| p99 | 310 ms | 770 ms | 1350 ms |
| Erreurs | 5.2% | 6.8% | 12.3% |

---

## 📊 T3 — Statistiques JVM

| Variante | CPU moy/pic | Heap moy/pic | GC (ms/s) | Threads | Connexions Hikari |
|----------|-------------|---------------|-----------|---------|-------------------|
| Jersey (A) | 26 / 65 % | 143 / 255 Mo | 14 / 39 | 71 / 80 | 12 / 16 |
| RestController (C) | 38 / 80 % | 320 / 467 Mo | 18 / 57 | 94 / 109 | 16 / 22 |
| Spring Data REST (D) | 41 / 92 % | 362 / 718 Mo | 30 / 88 | 99 / 126 | 18 / 27 |

---

## 🧪 T4 — Détails ENDPOINTS (JOIN-filter)

| Endpoint | Var. | RPS | p95 | Erreurs | Note |
|---------|------|-----|------|---------|-------|
| `GET /items?categoryId=` | A | 210 | 52 ms | 0% | JOIN FETCH, pas de N+1 |
| | C | 125 | 88 ms | 0.1% | Bonne stabilité |
| | D | 84 | 142 ms | 0.2% | HAL + lazy → surcharge |
| `GET /categories/{id}/items` | A | 95 | 75 ms | 0% | Full contrôle du JOIN |
| | C | 66 | 111 ms | 0.1% | Repository classique |
| | D | 42 | 198 ms | 0.2% | HAL génère + de charge |

---

## 🔄 T5 — Exemples MIXED

| Endpoint | Variante | RPS | p95 | Err | Commentaire |
|----------|----------|-----|-----|------|-------------|
| GET /items | A | 42 | 39 ms | 0% | Très rapide |
| | C | 36 | 51 ms | 0% | Stable |
| | D | 29 | 63 ms | 0.2% | HAL volumineux |
| POST /items | A | 10 | 179 ms | 5% | Sensible à la taille du body |
| | C | 8 | 227 ms | 6% | Latence similaire |
| | D | 5 | 340 ms | 10% | Plus de surcharge |
| DELETE /items | A/C/D | <4 | <40 ms | 0% | Peu coûteux |

---

## ⚠️ T6 — Incidents observés

| Run | Variante | Erreur | % | Cause | Fix |
|-----|----------|---------|----|--------|------|
| MIXED | A/C/D | 400 | 4–10% | JSON trop gros | Pré-processors |
| HEAVY | D | 400 | >10% | HAL + gros payload | Projections, pagination |
| JOIN | D | Lazy/N+1 | <1% | Pas de JOIN FETCH | Ajuster mapping |

---

## 🏁 T7 — Conclusion & Recommandation

| Critère | Vainqueur | Justification |
|---------|-----------|---------------|
| Débit (RPS) | **Jersey (A)** | Architecture légère, overhead minimal |
| Latence p95 | **Jersey (A)** | JSON plus simple, pipeline efficace |
| Stabilité | **RestController (C)** | Bon compromis performance/erreurs |
| Consommation ressources | **Jersey (A)** | CPU/RAM plus faibles |
| Facilité pour exposer les relations | **Spring Data REST (D)** | HATEOAS + endpoints automatiques |

---
