import { useEffect, useState } from 'react'

const apiConfig = {
  plans: {
    list: '/nutritionplan/displayall',
    create: '/nutritionplan/add',
    get: id => `/nutritionplan/${id}`,
    update: id => `/nutritionplan/update/${id}`,
    remove: id => `/nutritionplan/delete/${id}`,
  },
  foods: {
    list: '/foods',
    create: '/foods',
    get: id => `/foods/${id}`,
    update: id => `/foods/${id}`,
    remove: id => `/foods/${id}`,
  },
  users: {
    list: '/users',
    create: '/users',
    get: id => `/users/${id}`,
    update: id => `/users/${id}`,
    remove: id => `/users/${id}`,
  },
}

const emptyForms = {
  plans: { id: '', planName: '', dailyCalories: '', weeklyCost: '', active: true },
  foods: { id: '', name: '', category: '', caloriesPerServing: '', price: '', available: true },
  users: { id: '', fullName: '', email: '', age: '', heightCm: '', weightKg: '' },
}

const sectionLabels = { plans: 'Nutrition Plans', foods: 'Food Items', users: 'Users' }

export default function App() {
  const [section, setSection] = useState('plans')
  const [apiBase, setApiBase] = useState(localStorage.getItem('nutritionApiBase') || 'http://localhost:8080')
  const [savedBase, setSavedBase] = useState(localStorage.getItem('nutritionApiBase') || 'http://localhost:8080')
  const [items, setItems] = useState([])
  const [form, setForm] = useState({ ...emptyForms.plans })
  const [message, setMessage] = useState('')
  const [error, setError] = useState(false)
  const [busy, setBusy] = useState(false)

  async function request(path, options = {}) {
    const response = await fetch(`${savedBase.replace(/\/+$/, '')}${path}`, {
      ...options,
      headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    })
    const text = await response.text()
    let data = null
    if (text) {
      try { data = JSON.parse(text) } catch { data = text }
    }
    if (!response.ok) {
      const detail = typeof data === 'string' ? data : (data?.message || data?.error || JSON.stringify(data))
      throw new Error(`${response.status} ${response.statusText}${detail ? `: ${detail}` : ''}`)
    }
    return data
  }

  async function loadItems(currentSection = section) {
    setBusy(true)
    try {
      const data = await request(apiConfig[currentSection].list)
      const records = Array.isArray(data) ? data : (Array.isArray(data?.data) ? data.data : null)
      if (!records) throw new Error('Expected a JSON array. Check this service GET endpoint.')
      setItems(records)
      setMessage('')
      setError(false)
    } catch (e) {
      setItems([])
      setMessage(`Could not load ${sectionLabels[currentSection].toLowerCase()}: ${e.message}. Check that the backend and gateway are running, paths are correct, and CORS is enabled.`)
      setError(true)
    } finally {
      setBusy(false)
    }
  }

  useEffect(() => {
    setForm({ ...emptyForms[section] })
    loadItems(section)
    // Reload when the section changes.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [section, savedBase])

  function updateField(name, value) {
    setForm(current => ({ ...current, [name]: value }))
  }

  function applyGatewayUrl() {
    const clean = apiBase.trim().replace(/\/+$/, '')
    setApiBase(clean)
    setSavedBase(clean)
    localStorage.setItem('nutritionApiBase', clean)
    setMessage('API Gateway URL saved.')
    setError(false)
  }

  function clearForm() {
    setForm({ ...emptyForms[section] })
  }

  async function submitForm(event) {
    event.preventDefault()
    const id = form.id
    let body
    if (section === 'plans') {
      body = { planName: form.planName.trim(), dailyCalories: Number(form.dailyCalories), weeklyCost: Number(form.weeklyCost), active: Boolean(form.active) }
    } else if (section === 'foods') {
      body = { name: form.name.trim(), category: form.category.trim(), caloriesPerServing: Number(form.caloriesPerServing), price: Number(form.price), available: Boolean(form.available) }
    } else {
      body = { fullName: form.fullName.trim(), email: form.email.trim(), age: Number(form.age), heightCm: Number(form.heightCm), weightKg: Number(form.weightKg) }
    }

    try {
      await request(id ? apiConfig[section].update(id) : apiConfig[section].create, {
        method: id ? 'PUT' : 'POST',
        body: JSON.stringify(body),
      })
      setMessage(`${sectionLabels[section].slice(0, -1)} ${id ? 'updated' : 'created'} successfully.`)
      setError(false)
      clearForm()
      await loadItems(section)
    } catch (e) {
      setMessage(`Save failed: ${e.message}`)
      setError(true)
    }
  }

  async function editItem(item) {
    const id = item.id ?? item.planId ?? item.foodId ?? item.userId
    try {
      let record = await request(apiConfig[section].get(id))
      if (record?.data) record = record.data
      if (section === 'plans') {
        setForm({ id: record.id ?? record.planId ?? id, planName: record.planName ?? '', dailyCalories: record.dailyCalories ?? '', weeklyCost: record.weeklyCost ?? '', active: Boolean(record.active) })
      } else if (section === 'foods') {
        setForm({ id: record.id ?? record.foodId ?? id, name: record.name ?? '', category: record.category ?? '', caloriesPerServing: record.caloriesPerServing ?? '', price: record.price ?? '', available: Boolean(record.available) })
      } else {
        setForm({ id: record.id ?? record.userId ?? id, fullName: record.fullName ?? '', email: record.email ?? '', age: record.age ?? '', heightCm: record.heightCm ?? '', weightKg: record.weightKg ?? '' })
      }
      setMessage(`Editing record ${id}. Update the fields and click Save.`)
      setError(false)
      window.scrollTo({ top: 0, behavior: 'smooth' })
    } catch (e) {
      setMessage(`Could not load record for editing: ${e.message}`)
      setError(true)
    }
  }

  async function deleteItem(item) {
    const id = item.id ?? item.planId ?? item.foodId ?? item.userId
    if (!window.confirm(`Delete record ${id}? This cannot be undone.`)) return
    try {
      await request(apiConfig[section].remove(id), { method: 'DELETE' })
      setMessage('Record deleted successfully.')
      setError(false)
      if (String(form.id) === String(id)) clearForm()
      await loadItems(section)
    } catch (e) {
      setMessage(`Delete failed: ${e.message}`)
      setError(true)
    }
  }

  function renderFields() {
    const input = (name, label, type = 'text', props = {}) => (
      <label key={name}>{label}
        <input
          type={type}
          value={form[name] ?? ''}
          onChange={e => updateField(name, type === 'number' ? e.target.value : e.target.value)}
          required
          {...props}
        />
      </label>
    )
    const checkbox = (name, label) => (
      <label className="checkbox" key={name}>
        <input type="checkbox" checked={Boolean(form[name])} onChange={e => updateField(name, e.target.checked)} />
        {label}
      </label>
    )

    if (section === 'plans') return <>
      {input('planName', 'Plan name')}
      {input('dailyCalories', 'Daily calories', 'number', { min: 1 })}
      {input('weeklyCost', 'Weekly cost', 'number', { min: 0, step: '0.01' })}
      {checkbox('active', 'Active')}
    </>
    if (section === 'foods') return <>
      {input('name', 'Food name')}
      {input('category', 'Category')}
      {input('caloriesPerServing', 'Calories per serving', 'number', { min: 0 })}
      {input('price', 'Price', 'number', { min: 0, step: '0.01' })}
      {checkbox('available', 'Available')}
    </>
    return <>
      {input('fullName', 'Full name')}
      {input('email', 'Email', 'email')}
      {input('age', 'Age', 'number', { min: 1 })}
      {input('heightCm', 'Height (cm)', 'number', { min: 1, step: '0.1' })}
      {input('weightKg', 'Weight (kg)', 'number', { min: 1, step: '0.1' })}
    </>
  }

  function renderTableRow(item) {
    const id = item.id ?? item.planId ?? item.foodId ?? item.userId
    let cells
    if (section === 'plans') cells = [id, item.planName, item.dailyCalories, item.weeklyCost, item.active ? 'Yes' : 'No']
    else if (section === 'foods') cells = [id, item.name, item.category, item.caloriesPerServing, item.price, item.available ? 'Yes' : 'No']
    else cells = [id, item.fullName, item.email, item.age, item.heightCm, item.weightKg]

    return <tr key={id ?? JSON.stringify(item)}>
      {cells.map((cell, i) => <td key={i}>{cell === null || cell === undefined ? '' : String(cell)}</td>)}
      <td className="actions">
        <button type="button" className="small" onClick={() => editItem(item)}>Edit</button>
        <button type="button" className="small danger" onClick={() => deleteItem(item)}>Delete</button>
      </td>
    </tr>
  }

  const headings = section === 'plans'
    ? ['ID', 'Plan name', 'Calories/day', 'Weekly cost', 'Active', 'Actions']
    : section === 'foods'
      ? ['ID', 'Name', 'Category', 'Calories/serving', 'Price', 'Available', 'Actions']
      : ['ID', 'Full name', 'Email', 'Age', 'Height (cm)', 'Weight (kg)', 'Actions']

  return (
    <div className="app">
      <header className="hero">
        <div className="hero-inner">
          <p className="eyebrow">MICROSERVICES PROJECT</p>
          <h1>Nutrition Management System</h1>
          <p>Manage nutrition plans, food items, and user profiles in one place.</p>
        </div>
      </header>

      <main className="container">
        <section className="gateway card">
          <label htmlFor="apiBase">API Gateway URL</label>
          <input id="apiBase" value={apiBase} onChange={e => setApiBase(e.target.value)} />
          <button type="button" onClick={applyGatewayUrl}>Save URL</button>
        </section>

        <section className="stats">
          <div className="stat card"><span>Current section</span><strong>{sectionLabels[section]}</strong></div>
          <div className="stat card"><span>Records loaded</span><strong>{items.length}</strong></div>
          <div className="stat card"><span>Backend</span><strong>Gateway :8080</strong></div>
        </section>

        <nav className="tabs" aria-label="Management sections">
          {Object.keys(sectionLabels).map(key => (
            <button type="button" key={key} className={section === key ? 'tab active' : 'tab'} onClick={() => setSection(key)}>
              {sectionLabels[key]}
            </button>
          ))}
        </nav>

        <section className="card editor">
          <div className="section-heading">
            <div><p className="eyebrow">MANAGE RECORDS</p><h2>{form.id ? 'Edit' : 'Add'} {section === 'plans' ? 'Nutrition Plan' : section === 'foods' ? 'Food Item' : 'User Profile'}</h2></div>
            <button type="button" className="button-light" onClick={() => loadItems(section)} disabled={busy}>Refresh list</button>
          </div>
          <form onSubmit={submitForm} className="form-grid">
            {renderFields()}
            <div className="form-actions">
              <button type="submit" disabled={busy}>{form.id ? 'Update' : 'Save'} record</button>
              <button type="button" className="button-light" onClick={clearForm}>Clear</button>
            </div>
          </form>
        </section>

        <section className="card records">
          <div className="section-heading"><div><p className="eyebrow">DATABASE</p><h2>{sectionLabels[section]}</h2></div><span className="count">{items.length} records</span></div>
          {busy && <p className="muted">Loading records...</p>}
          {!busy && items.length === 0 && <p className="empty">No records to show yet. Add a record above or check the backend connection.</p>}
          {items.length > 0 && <div className="table-wrap"><table><thead><tr>{headings.map(h => <th key={h}>{h}</th>)}</tr></thead><tbody>{items.map(renderTableRow)}</tbody></table></div>}
        </section>

        {message && <div className={error ? 'notice error' : 'notice'} role="status">{message}</div>}
        <p className="footnote">Basic React frontend · Requests are sent through your API Gateway.</p>
      </main>
    </div>
  )
}
